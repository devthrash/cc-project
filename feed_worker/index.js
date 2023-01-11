'use strict';

const axios = require('axios')

axios.defaults.baseURL = require('./config').metadataServiceHost;

(async () => {
    // init amqp connection
    const amqp = await require('./lib/amqp').init();

    await amqp.consume(async (content, ack, reject) => {
        const {
            event,
            data
        } = content

        try {
            if (['post-created', 'post-updated'].includes(event)) {
                await axios.post('api/v1/metadata', data)
            }

            if (event === 'post-deleted') {
                await axios.delete(`api/v1/metadata/${data.uuid}`)
            }
        } catch (e) {
            reject(true)

            console.error('Re-queueing message due to:', e)
        }

        ack()
    })

    console.log('Consumer connected')

    process.on('SIGTERM', () => {
        amqp.cancel().then(() => {
            console.log('Consumer cancelled')
        })
    })
})()
