# Finobank

## Architecture diagram

![Architecture diagram](./Finobank.svg)

## How to start the application

The application uses docker-compose to start. All the docker images are hosted
at https://hub.docker.com/u/rohitkalaghatkar

### Prerequisites

- Docker

### Steps to run the application

- Clone the repository on local
- `cd docker-compose/finobank/`
- `docker-compose up -d`

## How to compile the application

### Prerequisites

- JDK 21
- Maven

### Steps to compile and build docker images of the application

1. Clone the repository on local
2. `mvn clean install jib:build` -- NOTE: This compiles and builds docker image and pushes the image to the author's
   docker private repository.
   INFO: `mvn clean install jib:dockerBuild` This compiles and build docker image and pushes the image to local
   repository

## Microservices hosted

1. Configuration server - http://localhost:8071/
2. Registry server - http://localhost:8070/
3. Gateway / Edge server - http://localhost:8072/
4. Accounts microservice - http://localhost:8080/
    - Postgres RDBMS - http://localhost:5432/accountsdb
5. Payments microservice - http://localhost:8081/
    - Postgres RDBMS - http://localhost:5433/paymentsdb
6. Users microservice - http://localhost:8082/
    - Postgres RDBMS - http://localhost:5434/usersdb
7. Keycloak / IAM service - http://localhost:8060/
    - Postgres RDBMS - http://localhost:5431/keycloakdb

## Configuration:

### Application users

- username: user1@finobank.com\
  password: **password**
- username: user2@finobank.com\
  password: **password**
- username: user3@finobank.com\
  password: **password**

### Database

All Postgres RDBMS database are configured to use below credentials:

- username: **postgres**
- password: **postgres**

### Swagger UI

There exists a know bug in application to access swagger ui from gateway
server [Accounts Gateway swagger ui](http://localhost:8072/docs/accounts/swagger-ui/index.html) URI. Instead, please use
swagger ui for individual microservices.

- Account: [Swagger UI](http://localhost:8080/swagger-ui/index.html)
- Payments: [Swagger UI](http://localhost:8081/swagger-ui/index.html)
- Users: [Swagger UI](http://localhost:8082/swagger-ui/index.html)

### Keycloak server

To access keycloak server [click here](http://localhost:8060/)

- username: **admin**
- password: **admin**

### Microservice registry dashboard

http://localhost:8070/

### postman

This tool is used to call application endpoints. Please download and install
from [here](https://www.postman.com/downloads/). After successful installation. Please import all the endpoints
configuration from postman folder. The postman configuration configures keycloak authentication endpoints and all the
application endpoints.

## Features implemented

### Main requirements implemented

- Authentication using keycloak IAM service
- List the bank accounts for authenticated user
- Create a single payment
- List all payments
- Delete a payment by ID
- Update password and userInfo - Currently only address can updated in via endpoint. To update password it is important
  that passwords are not handled by application and the password change is done via keycloak. So a user can login into
  his keycloak account and update his password and info. Account url for user1@finobank.com
  click [here](http://localhost:8060/realms/finobank/account/)
- Logout endpoint -- Not implemented

INFO: Currently, none of the three microservices are authenticated clients to Keycloak. An improvement would be to
authenticate all the clients with Keycloak. This way, the microservices could receive a client_id and client_secret,
authenticate, and generate tokens for internal communication.

### Optional requirements implemented

- Execute payment in single transaction -- Not implemented
- Pagination - Not implemented
- Fraud detection and block in case of more then 5 fraud payments
- Dockerized entire application
- Implemented OpenApi and Swagger documentation for microservices