'use strict';

const app = require('./app')({
    logger: true
});

(async () => {
    await app.listen({
        port: 9000,
        host: '0.0.0.0'
    })
})();
