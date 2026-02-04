# PillApp API
A private API for the mobile app PillApp
(Final project for the app development course in CEV Online)

## Prerequisites 

- Java 17

## Docs
- Usage documentation (in Spanish): [./doc/doc-es-v1.pdf](./docs/doc-es-v1.pdf)
- Model class diagram: [./doc/model.png](./docs/model.png) 

## Setup for local development (dev profile)

1. Import the app as a Maven project
2. Build and run (uses in-memory database with some default values)

## Working in dev profile

In 'dev' environment, some actuators and the H2 console are active:
- Actuators: http://localhost:8080/actuator/
- Console:  http://localhost:8080/h2-console
  - JDBC url: `jdbc:h2:mem:pillapp`
  - user `sa` and no password

## Setup environment for production

1. Start the MySQL server
2. With a root user, run the SQL commands in the file [mysql-setup/setup.sql](mysql-setup/setup.sql)
3. If needed, add some sample data with the script in [mysql-setup/data.sql](mysql-setup/data.sql)
4. Import the app as a Maven project, then `mvn install`
5. Set an environment var with name `JWT_SECRET` containing a 256-bit secret
6. Build and run the app with the maven profile "prod"

Note: To create a random secret on the terminal, use:

    echo $(date) | sha256sum
