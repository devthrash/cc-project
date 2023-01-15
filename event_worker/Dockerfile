FROM node:18.12.1

WORKDIR /app

COPY package.json .
COPY package-lock.json .

RUN npm ci && npm uninstall npm

COPY . .

CMD ["node", "index.js"]
