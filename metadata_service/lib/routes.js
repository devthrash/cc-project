'use strict';

/**
 * @param {import('fastify').FastifyInstance} fastify
 * @param {Object} options
 */
async function routes (fastify, options) {
    const collection = fastify.mongo.db.collection('posts')

    collection.createIndex({
        title: 'text',
        description: 'text',
        tag: 'text'
    })

    fastify.post(
        '/api/v1/metadata',
        {
            schema: {
                body: {
                    type: 'object',
                    properties: {
                        blogId: { type: 'number' },
                        title: { type: 'string' },
                    },
                    required: ['blogId']
                }
            }
        },
        async (request, reply) => {
            const { body } = request;

            const query = {
                blogId: body.blogId
            }

            await collection.updateOne(query, { $set: body }, { upsert: true })

            return {
                result: {
                    ok: true
                }
            }
        }
    )

    fastify.patch(
        '/api/v1/metadata',
        {
            schema: {
                body: {
                    type: 'object',
                    properties: {
                        blogId: { type: 'number' },
                        views: { const: 1 }
                    },
                    required: ['blogId']
                }
            }
        },
        async (request, reply) => {
            const { body } = request;

            const query = {
                blogId: body.blogId
            }

            const update = {}

            if (body.views) {
                update['$inc'] = { views: body.views }
            }

            if (Object.keys(update).length) {
                await collection.updateOne(query, update)
            }

            return {
                result: {
                    ok: true
                }
            }
        }
    )

    fastify.get(
        '/api/v1/metadata/:blogId',
        async (request, reply) => {
            const { params } = request;

            const metadata = await collection.findOne({ blogId: params.blogId })

            if (metadata) {
                return {
                    result: {
                        ...metadata,
                        ok: true
                    }
                }
            }

            reply.status(404)
            return {
                result: {
                    ok: false
                }
            }
        }
    )

    fastify.delete(
        '/api/v1/metadata/:blogId',
        async (request, reply) => {
            const { params } = request;

            await collection.deleteOne({ blogId: params.blogId })

            return {
                result: {
                    ok: true
                }
            }
        }
    )

    fastify.post(
        '/api/v1/metadata/search-text',
        {
            schema: {
                body: {
                    type: 'object',
                    properties: {
                        text: { type: 'string' }
                    },
                    required: ['text']
                }
            }
        },
        async (request, reply) => {
            const { body } = request;

            const query = {
                $text: { $search: body.text }
            }

            return {
                result: {
                    posts: await collection.find(query).limit(20).toArray(),
                    ok: true
                }
            }
        }
    )
}

module.exports = routes
