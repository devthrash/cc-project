'use strict';

const config = {
    amqp: {
        user: process.env.AMQP_USER,
        password: process.env.AMQP_PASS,
        host: process.env.AMQP_HOST,
        port: process.env.AMQP_PORT,
        queueName: process.env.AMPQ_QUEUE,
        queueAssertions: {
            durable: true
        },
        channelOpts: {
            prefetch: 1
        }
    },
    metadataServiceHost: process.env.METADATA_SERVICE_HOST
}

module.exports = config
