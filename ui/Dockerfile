# Stage 1 - Create a builder container
FROM node:12.8-buster-slim as test

RUN mkdir /usr/src/app
WORKDIR /usr/src/app

COPY src /usr/src/app/src
COPY public /usr/src/app/public
COPY .rescriptsrc.js /usr/src/app/.rescriptsrc.js
COPY .babelrc /usr/src/app/.babelrc
COPY package.json /usr/src/app/package.json
COPY package-lock.json /usr/src/app/package-lock.json
RUN npm install

#CMD ["npm", "start"]
FROM test as builder
RUN npm run build

# Stage 2 - Create a downsized production container

FROM nginx:1.12-alpine as prod
COPY --from=builder /usr/src/app/build /usr/share/nginx/html
#COPY nginx.conf /etc/nginx/conf.d/default.conf
EXPOSE 80
EXPOSE 443
CMD ["nginx", "-g", "daemon off;"]
