# DDD template

### Table of Contents

1. [Overview](#Overview)
2. [Used Tech](#used-tech)
3. [How to run](#how-to-run)
4. [Functionalities](#functionalities)
6. [Models](#Models)
7. [Data Transfer Objects (DTO)](#data-transfer-objects-dto)
8. [Endpoints](#endpoints)
9. [Project Structure](#project-structure)

## Overview

DDD Template. Hexagonal + DDD

## Used Tech

1) Java 11
2) Spring boot
3) Maven
4) PostgreSQL
5) H2 Database (For testing)
6) Docker

## How to run

### Using Docker

#### 1) Install docker

Use this [link](https://docs.docker.com/get-docker/) to install docker:

**Note**: Make sure docker is recognized by classpath.

#### 2) Run docker-compose

Run the command below from the project directory to start creating containers in docker
using `docker-compose.yaml`
file as below: (you can use the [project structure](#project-structure) section to
locate `docker-compose.yaml` file)

`Project_Dir> docker-compose up`

**Note**: Docker compose environment variables can be changed from `.env` file in the project
directory which can be found from [project structure](#project-structure) section. Default valuse
are as below:

##### .env file

```
PROFILE=docker             # Runs docker profile from application.yml from the project
APP_PORT=8080              # The port that the application will be served from
DB_HOST=postgres_example   # The network host name from the postgres container 
DB_USER=postgres           # Database username
DB_PASSWORD=postgres       # Database password
DB=mydb                    # Database name
DB_PORT=5432               # The postgres container port number
```

### Using Maven

**Note**: Settings regarding the web application (port number, postgres settings, and ... ) can be
changed in `application-prod.yml` file which can be found from
the [project structure](#project-structure).

#### 1) Install [Java 11](https://docs.oracle.com/en/java/javase/11/install/overview-jdk-installation.html)

#### 2) Install [maven](https://maven.apache.org/install.html)

#### 3) Install [postgreSQL](https://www.postgresql.org/download/)

**Note**: If installing postgresSQL is not required, docker can be used with
Command `docker-compose up postgres` that can be run from
the [project directory](#project-structure) to just start docker containers regarding PostgreSQL.

**Note**: PostgreSQL settings for the web application can be changed
in [application-prod.yml](#project-structure) (if using docker-compose, the affiliated postgres
settings should be changed in `docker-compose.yml` as well). Default values are as below:

```
 driver-class-name: org.postgresql.Driver
 url: jdbc:postgresql://localhost:5432/mydb
 username: postgres
 password: postgres
```

#### 4) Build the project

From the [project directory](#project-structure), which contains `pom.xml`, run the maven command
below:

`Project_Dir> mvn clean package -DPROFILE=prod`

#### 5) Run the .jar file

From the `./target/` directory in the [project's directory](#project-structure) (`/target` is
created when project is built with maven), run the command below:

`java -jar -DPROFILE=prod api-0.0.1-SNAPSHOT.jar`

**Note** If Maven build in [step 4](#4-build-the-project) does not complete successfully, the `.jar`
file will not be created in `./target/` directory

**Note**: If the `.jar` file name is changed, the associated name in the command that runs
the `.jar` file should change as well.

## Functionalities



1) Customer:
    1) Create Customer (explore [endpoint](#post-customer))
    2) Update Customer (explore [endpoint](#put-customer))
    3) Get Customer (explore [endpoint](#get-customerid))
2) order:

## Models

![Models UML Diagram](./design/uml_design.jpg)'

#### Customer

Customer

|         Name |    Index    |  Type  | Description |
|-------------:|:-----------:|:------:|-------------|
| `identifier` | Primary Key |  UUID  |             |
|       `name` |             | String |             |

#### Order

Order

|                 Name |    Index    |  Type  | Description |
|---------------------:|:-----------:|:------:|-------------|
|         `identifier` | Primary Key |  UUID  |             |
|        `productName` |             | String |             |
| `customerIdentifier` |             |  UUID  |             |

## Data Transfer Objects (DTO)

#### CustomerData

Customer

|         Name | Json Ignore |  Type  | Description |
|-------------:|:-----------:|:------:|-------------|
| `identifier` |             | String |             |
|       `name` |             | String |             |

#### OrderData

Order

|                Name | Json Ignore |         Type          | Description |
|--------------------:|:-----------:|:---------------------:|-------------|
|        `identifier` |             |        String         |             |
|       `productName` |             |        Double         |             |
| `customerReference` |             | CustomerReferenceData |             |

#### CustomerReferenceData

Customer reference

|         Name | Json Ignore |  Type  | Description |
|-------------:|:-----------:|:------:|-------------|
| `identifier` |             | String |             |

## Endpoints

Users can access this RESTful API through the endpoints below:

### Initial Project url:

`http://localhost:8080`

Note: you can change the port from [.env](#env-file) file or from
the [application-prod.yml](#project-structure) if not using docker.

### To test the endpoints, two options are provided:

### Option 1: Use Swagger

Ables access to the endpoints through a user interface using the link below:

`http://localhost:8080/api/swagger-ui.html`

Note: you can change the port from [.env](#env-file) file or from
the [application-prod.yml](#project-structure) if not using docker.

### Option 2: Use Curl, Postman, or any API testing software

To use in any API testing software, the endpoint urls are provided as below:



---

##### Customer Endpoints

###### GET

`Get` [http://localhost:8080/customer/{id}](#get-customerid) <br/>

###### POST

`Create` [http://localhost:8080/customer](#post-customer) <br/>

###### PUT

`Update` [http://localhost:8080/customer](#put-customer) <br/>




----

### GET /customer/{id}

Gets customer

File: [CustomerApplication.java](#project-structure)

**Parameters**

**Path Variable**

identifier

**Request Body**

|         Name | Required |  Type  | Description |
|-------------:|:--------:|:------:|-------------|
| `identifier` |          | String |             |
|       `name` |          | String |             |

**Response**

A [CustomerData](#customerdata) object

```

{
   "identifier" : ""
   "name" : ""
}
```

---

### POST /customer

Create customer

File: [CustomerApplication.java](#project-structure)

**Parameters**

None

**Path Variable**

None

**Request Body**

A [CustomerData](#customerdata) object

**Response**

A list of [CustomerData](#customerdata) object

```
{
  "identifier": "",
  "name": ""
}
```

---

### PUT /customer

Update

File: [CustomerApplication.java](#project-structure)

**Parameters**

None

**Path Variable**

None

**Request Body**

A [CustomerData](#customerdata) object

**Response**

A list of [CustomerData](#customerdata) object

```
{
  "identifier": "",
  "name": ""
}
```

---

---

### Project Structure

The structure of the project is as below:

    .
    ├── ...
    ├── design
    │   └── UML_diagram.jpg
    ├── documentation           # Documentation files (alternatively `doc`)
    │   ├── ...
    │   ├── index.html
    │   └── ... 
    │
    ├── application
    │   ├── src
    │   │   ├── main --> apis 
    │   │   └── test --> integration tests 
    │   └── target 
    ├── domain
    │   ├── bean --> service beans
    │   ├── model --> exchage model
    │   ├── port
    │   │   ├── dto --> data transfer objects for incomming and outgoing ports
    │   │   ├── incoming --> incoming command and query service interfaces
    │   │   └── outgoing --> outgoing command and query repository interfaces
    │   ├── service
    │   │   ├── command --> command services 
    │   │   ├── orchestration --> orchestration services
    │   │   └── query --> query services
    │   └── test
    │       ├── data --> data models for testing 
    │       ├── mocks --> mockings for the outgoing command and query repositories 
    │       ├── personas --> persona data for testing 
    │       └── steps --> general step defs and parameter matchings
    ├── infrastruction
    │   └── src --> outgoing repository implementations from the domain
    ├── framework
    ├── .env                    # Docker compose environement variables 
    ├── docker-compose.yaml     # Docker compose file containing this web applicationa and postgres containers 
    ├── Dockerfile              # Dockerfile for creating image for the web application to start a container in docker
    ├── pom.xml
    ├── README.md
    └── ...

