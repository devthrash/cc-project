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
    mongo: {
        username: process.env.MONGO_USER,
        password: process.env.MONGO_PASS,
        host: process.env.MONGO_HOST,
        port: process.env.MONGO_PORT,
        dbName: process.env.MONGO_DB,
        collectionName: 'posts'
    }
}

module.exports = config
