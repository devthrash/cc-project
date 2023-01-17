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
        tags: 'text'
    })
    collection.createIndex({
        created_at: 1
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

            const now = new Date()
            const doc = {
                $set: {
                    ...body,
                    updated_at: now
                },
                $setOnInsert: {
                    created_at: now
                }
            }

            await collection.updateOne(query, doc, { upsert: true })

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
                        op: { enum: ['inc_comment_count'] }
                    },
                    required: ['blogId', 'op']
                }
            }
        },
        async (request, reply) => {
            const { body } = request;

            const query = {
                blogId: body.blogId
            }

            const update = {}

            if (body.op === 'inc_comment_count') {
                update['$inc'] = { comment_count: 1 }
            }

            // if (Object.keys(update).length) {
                await collection.updateOne(query, update)
            // }

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

    fastify.get(
        '/api/v1/metadata/all',
        async (request, reply) => {
            const { body } = request;

            return {
                result: {
                    posts: await collection.find({}).sort({created_at: -1}).toArray(),
                    ok: true
                }
            }
        }
    )
}

module.exports = routes
