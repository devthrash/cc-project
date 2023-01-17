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
            if (['post-created', /*'post-updated'*/].includes(event)) {
                const doc = {
                    blogId: data.blogId,
                    title: data.title,
                    description: data.description,
                    tags: []
                }

                if (data.tag) {
                    doc.tags.push(data.tag)
                }

                await axios.post('api/v1/metadata', doc)
            }

            if (event === 'post-deleted') {
                await axios.delete(`api/v1/metadata/${data.blogId}`)
            }

            if (event === 'comment-created') {
                const { blogId } = data

                await axios.patch('api/v1/metadata', {
                    blogId,
                    op: 'inc_comment_count'
                })
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
