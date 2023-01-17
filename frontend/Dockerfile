FROM node:slim as builder

ARG BACKEND_HOST_ARG

# create working directory
RUN mkdir /build
WORKDIR /build

# install build depedencies
COPY package.json .
COPY package-lock.json .
RUN npm install

# copy source code and other files
COPY . .

ENV NODE_ENV production

# set backend host
ENV BACKEND_HOST ${BACKEND_HOST_ARG}

# build the app
RUN npm run build

# build nginx container
FROM nginx:stable-alpine

# remove default configs
RUN rm /etc/nginx/nginx.conf /etc/nginx/conf.d/default.conf

# copy builded files
COPY --from=builder /build/dist /usr/share/nginx/html

# copy nginx config gile
COPY deploy/nginx.conf /etc/nginx

#expose port
EXPOSE 80
