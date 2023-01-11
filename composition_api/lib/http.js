const { create } = require('axios')

const metadataApi = create({
    baseURL: process.env.METADATA_SERVICE_HOST
})

module.exports = {
    metadataApi
}
