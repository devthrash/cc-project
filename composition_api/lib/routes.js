'use strict';

const { metadataApi, blogsApi } = require('./http')

/**
 * @param {import('fastify').FastifyInstance} fastify
 * @param {Object} options
 */
async function routes (fastify, options) {
    fastify.get(
        '/api/v1/posts/search',
        {
            schema: {
                querystring: {
                    type: 'object',
                    properties: {
                        text: { type: 'string' }
                        // mostViewed: { const: 1 },
                    },
                    required: ['text']
                }
            }
        },
        async (request, reply) => {
            const { query } = request;

            const response = await metadataApi.post('api/v1/metadata/search-text', { text: query.text })

            return {
                posts: response.data.result.posts
            }
        }
    )

    fastify.get(
        '/api/v1/posts/:id',
        async (request, reply) => {
            const { params } = request;

            const response = await blogsApi.get('blogs/' + params.id)

            return {
                post: response.data
            }
        }
    )

    fastify.post(
        '/api/v1/posts',
        {
            schema: {
                body: {
                    type: 'object',
                    properties: {
                        tag: { type: 'string' },
                        title: { type: 'string', minLength: 3 },
                        description: { type: 'string', minLength: 10 },
                    },
                    required: ['title', 'description']
                }
            }
        },
        async (request, reply) => {
            const { body } = request;

            const response = await blogsApi.post('blogs', body)

            return {
                post: response.data
            }
        }
    )
}

module.exports = routes
