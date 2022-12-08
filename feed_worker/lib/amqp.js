'use strict';

const amqp = require("amqplib");
const { v4: uuidV4 } = require('uuid');

const config = require("../config");

const buildUrl = () => {
    return `amqp://${config.amqp.user}:${config.amqp.password}@${config.amqp.host}:${config.amqp.port}/`
}

const createHandlerWrapper = (channel, handler) => {
    return (message) => {
        const ack = () => {
            channel.ack(message)
        }

        const reject = (requeue) => {
            channel.reject(message, requeue)
        }

        // parse message body
        const {
            contentType = 'application/json'
        } = message.properties

        let content = null;
        try {
            content = JSON.parse(message.content)
        } catch (e) {
            // reject without re-queueing
            return reject(false)
        }

        handler(content, ack, reject)
    }
}

const createChannel = async (connection) => {
    const channel = await connection.createChannel();

    await channel.prefetch(config.amqp.channelOpts.prefetch)
    await channel.assertQueue(config.amqp.queueName, config.amqp.queueAssertions)

    return channel
}

module.exports = {
    init: async () => {
        const consumerTag = uuidV4();

        const connection = await amqp.connect(buildUrl());
        const channel = await createChannel(connection)

        return {
            consume: async (handler) => {
                await channel.consume(config.amqp.queueName, createHandlerWrapper(channel, handler), {
                    consumerTag,
                    noAck: false
                })
            },
            cancel: async () => {
                await channel.cancel(consumerTag)
            }
        }
    }
}
