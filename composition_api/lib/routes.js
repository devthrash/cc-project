'use strict';

const { metadataApi } = require('./http')

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
}

module.exports = routes
