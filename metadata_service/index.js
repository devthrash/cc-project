'use strict';

const config = {
    logger: true,
    mongo: {
        url: process.env.MONGO_URL
        // url: 'mongodb://test_db_user:password@localhost:27017/test_db'
    }
}

const app = require('./app')(config);

(async () => {
    await app.listen({
        port: 9000,
        host: '0.0.0.0'
    })
})();
