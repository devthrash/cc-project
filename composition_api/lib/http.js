const { create } = require('axios')

const metadataApi = create({
    baseURL: process.env.METADATA_SERVICE_HOST
})

const blogsApi = create({
    baseURL: process.env.BLOGS_API_HOST
})

module.exports = {
    metadataApi,
    blogsApi
}
