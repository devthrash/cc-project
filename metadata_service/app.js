'use strict';

const fastify = require('fastify')

function build(opts) {
    const app = fastify(opts);

    app.register(require('@fastify/mongodb'), {
        url: opts.mongo.url
    });
    app.register(require('./lib/routes'));

    return app;
}

module.exports = build
