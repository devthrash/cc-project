'use strict';

const { MongoClient } = require("mongodb");

const config = require('./../config');

const buildConnectionString = () => {
    return 'mongodb://'
        + `${config.mongo.username}:${config.mongo.password}` // credentials
        + '@'
        + `${config.mongo.host}:${config.mongo.port}` // host
        + '/'
        + config.mongo.dbName
}

module.exports = {
    init: () => {
        const client = new MongoClient(buildConnectionString())
        const db = client.db(config.mongo.dbName);

        return {
            db,
            collection: db.collection(config.mongo.collectionName)
        }
    }
}
