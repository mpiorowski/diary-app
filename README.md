# Diary application

Simple diary application with moderator functionality.

- Diary application with moderator functionality.
- Data export to excel.
- Postgres logging and auditing.
- Authorization via jwt tokens.
- Rest using encrypted uuid

Frontend  -> react + antd  
Backend   -> spring boot + flyway + mybatis + postgresql

# Deployment

## Prerequisites and Dependencies
For prod/test deployment using docker the only prerequisites is docker/docker-compose.

Prerequisites: node, npm, java, gradle, docker, docker-compose

Install dependencies:
```
./api/gradlew -p ./api/ build --refresh-dependencies
```
```
npm --prefix ./ui install ./ui
```

## Development deployment

### database
PostgreSQL 10.5 (configuration in application.yml):
port:   5444
scheme: pbs
user:   admin
pass:   admin

or You can use docker-compose to setup simple database container
```
docker-compose -f ./dev/docker-compose.dev.yml up -d --build
```

### frontend and backend
Run two separate shell windows for frontend and backend.
(Or just use your favorite IDE :) )
```
./api/gradlew -p ./api/ bootRun
```
```
npm --prefix ./ui start
```

### Access
Access via http://localhost:3000

Admin account:
username: admin
password: pass

Users account:
usernames: user1, user2, user3
password: pass

## Production/Test deployment using docker-compose with downsized containers

(Not sure if still works :) )

Production and test environments are set up using multistage docker containers.
The only prerequisites here is docker/docker-compose.
Production deployment need a working ssl certificate, configured in prod/nginx.conf file.

prod:
```
sh prod/prod.sh
```

test:
```
sh test/test.sh
```
