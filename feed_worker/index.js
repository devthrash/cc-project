'use strict';

(async () => {
    // init mongodb connection
    const { collection } = require('./lib/mongoUtils').init()

    // init amqp connection
    const amqp = await require('./lib/amqp').init();

    await amqp.consume(async (content, ack, reject) => {
        const {
            event,
            data
        } = content

        const query = {
            uuid: data.uuid
        }

        try {
            if (['post-created', 'post-updated'].includes(event)) {
                await collection.updateOne(query, { $set: data }, {
                    upsert: true
                })
            }

            if (event === 'post-deleted') {
                await collection.deleteOne(query)
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
