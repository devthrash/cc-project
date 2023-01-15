FROM node:slim as builder

# create working directory
RUN mkdir /build
WORKDIR /build

# install build depedencies
COPY package*.json ./
RUN npm ci

# copy source code and other files
#COPY src .src/
#COPY public .public/
COPY . .

# set env; webpack and other modules have configs for production
ENV NODE_ENV production

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
