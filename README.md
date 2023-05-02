# Elemica Transportation Management System

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

DDD Template

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


Run the command below from the project directory to start creating containers in docker using `docker-compose.yaml`
file as below: (you can use the [project structure](#project-structure) section to locate `docker-compose.yaml` file)


`Project_Dir> docker-compose up`

**Note**: Docker compose environment variables can be changed from `.env` file in the project directory which can be found from [project structure](#project-structure) section. Default valuse are as below:

##### .env file
```
PROFILE=docker             # Runs docker profile from application.yml from the project
APP_PORT=8080              # The port that the application will be served from
DB_HOST=postgres_elemica   # The network host name from the postgres container 
DB_USER=postgres           # Database username
DB_PASSWORD=postgres       # Database password
DB=elemica_db              # Database name
DB_PORT=5432               # The postgres container port number
```


### Using Maven

**Note**: Settings regarding the web application (port number, postgres settings, and ... ) can be changed in `application-prod.yml` file which can be found from the [project structure](#project-structure).



#### 1) Install [Java 11](https://docs.oracle.com/en/java/javase/11/install/overview-jdk-installation.html)
#### 2) Install [maven](https://maven.apache.org/install.html)
#### 3) Install [postgreSQL](https://www.postgresql.org/download/)

**Note**: If installing postgresSQL is not required, docker can be used with Command `docker-compose up postgres` that can be run from the [project directory](#project-structure) to just start docker containers regarding PostgreSQL.

**Note**: PostgreSQL settings for the web application can be changed in [application-prod.yml](#project-structure) (if using docker-compose, the affiliated postgres settings should be changed in `docker-compose.yml` as well). Default values are as below:

```
 driver-class-name: org.postgresql.Driver
 url: jdbc:postgresql://localhost:5432/elemica_db
 username: postgres
 password: postgres
```


#### 4) Build the project

From the [project directory](#project-structure), which contains `pom.xml`, run the maven command below:

`Project_Dir> mvn clean package -DPROFILE=prod`



#### 5) Run the .jar file

From the `./target/` directory in the [project's directory](#project-structure) (`/target` is created when project is built with maven), run the command below:

`java -jar -DPROFILE=prod api-0.0.1-SNAPSHOT.jar`

**Note** If Maven build in [step 4](#4-build-the-project) does not complete successfully, the `.jar` file will not be created in `./target/` directory


**Note**: If the `.jar` file name is changed, the associated name in the command that runs the `.jar` file should change as well.




## Functionalities

Using this API, users can create shipments and assign tariff and vehicles to them. The API's functionalities are as below:

1) Shipment:
    1) Get a shipment by its name (explore [endpoint](#get-shipmentsname))
    2) Get all shipments (explore [endpoint](#get-shipments))
    3) Create a new shipment (explore [endpoint](#post-shipments))
    4) Update a shipment by its name (explore [endpoint](#put-shipments))
    5) Remove a shipment by its name (explore [endpoint](#delete-shipmentsname))
    6) Get the most expensive shipment by the total price which is the price of shipment taking into account the tariff assigned to it (explore [endpoint](#get-shipmentsmost-expensive))
2) Tariff:
    1) Get a tariff by its name (explore [endpoint](#get-tariffsname))
    2) Get all tariffs (explore [endpoint](#get-tariffs))
    3) Create a new tariff (explore [endpoint](#post-tariffs))
    4) Update a tariff by its name (explore [endpoint](#put-tariffs))
    5) Remove a tariff by its name (explore [endpoint](#delete-tariffsname))
3) Vehicle:
    1) Get a vehicle by its name (explore [endpoint](#get-vehiclesname))
    2) Get all vehicle (explore [endpoint](#get-vehicles))
    3) Create a new vehicle (explore [endpoint](#post-vehicles))
    4) Update a vehicle by its name (explore [endpoint](#put-vehicles))
    5) Set the availability of a vehicle (explore [endpoint](#post-vehiclesvehicleavailableavailable))
    6) Remove a vehicle by its name (explore [endpoint](#delete-vehiclesname))
4) Assignments:
    1) Assign vehicle to shipment (explore [endpoint](#post-assignvehiclevehicletoshipment))
    2) Assign tariff to shipment (explore [endpoint](#post-assigntarifftarifftoshipment))
    3) Automatically assign the best tariff with lowest `(rate * (1 - discount))` and vehicle with enough capacity to a shipment (explore [endpoint](#post-assigntoshipmentauto))
    4) Revoke assignment of vehicle and tariff from shipment (explore [endpoint](#post-assignrevokeshipment))
    5) Add vehicle to tariff: tariffs have applicable vehicles (explore [endpoint](#post-assigntariff-vehiclevehicletotariff))
    6) Remove vehicle from tariff: tariffs have applicable vehicles (explore [endpoint](#post-assignremovetariff-vehiclevehiclefromtariff))


## Models

![Models UML Diagram](./design/uml_design.jpg)'


#### Shipment

Model for shipment information, and tariff and vehicle assignment to the shipment.

|         Name |                 Index                 |     Type      | Description                                                |
|-------------:|:-------------------------------------:|:-------------:|------------------------------------------------------------|
|       `name` |              Primary Key              |    String     | Name of the shipment                                       |
|     `weight` |                                       |    Double     | Weight of the shipment                                     |
|      `price` |                                       |    Double     | Price of the shipment                                      |
| `totalPrice` |                                       |    Double     | Total price of the shipment with tariff being counted      |
|   `currency` |                                       |    String     | Applicable currency                                        |
|     `status` |                                       | ShipmentState | Status of the shipment (created, ready, shipped, received) |
|    `vehicle` | Vehicle.name as Foreign Key (indexed) |    Vehicle    | An instance of vehicle assigned to shipment                |
|     `tariff` | Tariff.name as Foreign Key (indexed)  |    Tariff     | An instance of tariff assigned to shipment                 |


#### Tariff

Model indicating the tariff.

|        Name |                Index                 |      Type      | Description                                     |
|------------:|:------------------------------------:|:--------------:|-------------------------------------------------|
|      `name` |            Primary Key               |     String     | Name of the tariff                              |
|      `rate` |                                      |     Double     | Rate of the tariff                              |
|  `discount` |                                      |     Double     | Discount of the tariff                          |
| `shipments` |                                      | List<Shipment> | A list of shipments tariff has been assigned to |
| `vehicles`  |                                      | List<Vehicle>  | A list of applicable vehicles                   |

#### Vehicle

Model for vehicle information.

|        Name |                Index                 |      Type      | Description                                                 |
|------------:|:------------------------------------:|:--------------:|-------------------------------------------------------------|
|    `name`   |            Primary Key               |     String     | Name of the tariff                                          |
|  `capacity` |                                      |     Double     | Rate of the tariff                                          |
| `available` |                                      |    Boolean     | Discount of the tariff                                      |
| `shipments` |                                      | List<Shipment> | A list of shipments tariff has been assigned to             |
|   `tariffs` |                                      |  List<Tariff>  | A list of tariffs vehicle is added to as applicable vehicle |


## Data Transfer Objects (DTO)


#### ShipmentDTO

DTO object for shipment information, and tariff and vehicle assignment to the shipment.

|         Name | Json Ignore |     Type      | Description                                                |
|-------------:|:-----------:|:-------------:|------------------------------------------------------------|
|       `name` |             |    String     | Name of the shipment                                       |
|     `weight` |             |    Double     | Weight of the shipment                                     |
|      `price` |             |    Double     | Price of the shipment                                      |
| `totalPrice` |             |    Double     | Total price of the shipment with tariff being counted      |
|   `currency` |             |    String     | Applicable currency                                        |
|     `status` |             | ShipmentState | Status of the shipment (created, ready, shipped, received) |
|    `vehicle` |  Read Only  |    Vehicle    | An instance of vehicle assigned to shipment                |
|     `tariff` |  Read Only  |    Tariff     | An instance of tariff assigned to shipment                 |


#### TariffDTO

DTO object indicating  tariff.

|        Name | Json Ignore   |      Type      | Description                                     |
|------------:|:-------------:|:--------------:|-------------------------------------------------|
|      `name` |               |     String     | Name of the tariff                              |
|      `rate` |               |     Double     | Rate of the tariff                              |
|  `discount` |               |     Double     | Discount of the tariff                          |
| `shipments` |    Ignore     | List<Shipment> | A list of shipments tariff has been assigned to |
| `vehicles`  |   Read Only   | List<Vehicle>  | A list of applicable vehicles                   |

#### VehicleDTO

DTO object for vehicle information.

|        Name | Json Ignore  |      Type      | Description                                                 |
|------------:|:------------:|:--------------:|-------------------------------------------------------------|
|    `name`   |              |     String     | Name of the tariff                                          |
|  `capacity` |              |     Double     | Rate of the tariff                                          |
| `available` |              |    Boolean     | Discount of the tariff                                      |
| `shipments` |    Ignore    | List<Shipment> | A list of shipments tariff has been assigned to             |
|   `tariffs` |    Ignore    |  List<Tariff>  | A list of tariffs vehicle is added to as applicable vehicle |







## Endpoints


Users can access this RESTful API through the endpoints below:

### Initial Project url:
`http://localhost:8080`

Note: you can change the port from [.env](#env-file) file or from the [application-prod.yml](#project-structure) if not using docker.

### To test the endpoints, two options are provided:


### Option 1: Use Swagger

Ables access to the endpoints through a user interface using the link below:

`http://localhost:8080/api/swagger-ui.html`


Note: you can change the port from [.env](#env-file) file or from the [application-prod.yml](#project-structure) if not using docker.


### Option 2: Use Curl, Postman, or any API testing software

To use in any API testing software, the endpoint urls are provided as below:



---
##### Shipment Endpoints



###### GET
`Gets shipment by name` [http://localhost:8080/shipments/{name}](#get-shipmentsname) <br/>
`Get all shipments` [http://localhost:8080/shipments/?page={page}&size={size}](#get-shipments) <br/>
`Get most expensive shipment` [http://localhost:8080/shipments/most-expensive](#get-shipmentsmost-expensive) <br/>

###### POST
`Insert a new shipment` [http://localhost:8080/shipments](#post-shipments) <br/>

###### PUT
`Update a shipment` [http://localhost:8080/shipments](#put-shipments) <br/>

###### DELETE
`Deletes shipment by name` [http://localhost:8080/shipments/{name}](#delete-shipmentsname) <br/>


---
##### Tariff Endpoints




###### GET
`Gets tariff by name` [http://localhost:8080/tariffs/{name}](#get-tariffsname) <br/>
`Get all tariffs` [http://localhost:8080/tariffs/?page={page}&size={size}](#get-tariffs) <br/>

###### POST
`Insert a new tariff` [http://localhost:8080/tariffs](#post-tariffs) <br/>

###### PUT
`Update a tariff` [http://localhost:8080/tariffs](#put-tariffs) <br/>

###### DELETE
`Deletes shipment by name` [http://localhost:8080/tariffs/{name}](#delete-tariffsname) <br/>


---
##### Vehicle Endpoints




###### GET
`Gets vehicle by name` [http://localhost:8080/vehicles/{name}](#get-vehiclesname) <br/>
`Get all vehicles` [http://localhost:8080/vehicles/?page={page}&size={size}](#get-vehicles) <br/>

###### POST
`Insert a new vehicle` [http://localhost:8080/vehicles](#post-vehicles) <br/>
`Sets vehicle availability` [http://localhost:8080/vehicles/{vehicle}?available={available}](#post-vehiclesvehicleavailableavailable) <br/>

###### PUT
`Update a vehicle` [http://localhost:8080/vehicles](#put-vehicles) <br/>

###### DELETE
`Deletes vehicle by name` [http://localhost:8080/vehicles/{name}](#delete-vehiclesname) <br/>


---
##### Assignments Endpoints




###### POST
`Assign vehicle to shipment` [http://localhost:8080/assign/vehicle/{vehicle}/to/{shipment}](#post-assignvehiclevehicletoshipment) <br/>
`Assign tariff to shipment` [http://localhost:8080/assign/tariff/{tariff}/to/{shipment}](#post-assigntarifftarifftoshipment) <br/>
`Assign cheapest tariff and vehicle with capacity to shipment` [http://localhost:8080/assign/to/{shipment}/auto](#post-assigntoshipmentauto) <br/>
`Revoke vehicle and tariff assignments from shipment` [http://localhost:8080/assign/revoke/{shipment}](#post-assignrevokeshipment) <br/>
`Add vehicle to tariff as applicable vehicle` [http://localhost:8080/assign/tariff-vehicle/{vehicle}/to/{tariff}](#post-assigntariff-vehiclevehicletotariff) <br/>
`Remove vehicle from tarrif's list of vehicles` [http://localhost:8080/assign/remove/tariff-vehicle/{vehicle}/from/{tariff}](#post-assignremovetariff-vehiclevehiclefromtariff) <br/>


----
### GET /shipments/{name}
Gets the required information for a shipment using its name.

File: [ShipmentController.java](#project-structure)

**Parameters**

None

**Path Variable**

|   Name | Required |  Type  | Description          |
|-------:|:--------:|:------:|----------------------|
| `name` | required | String | name of the shipment |


**Request Body**

None


**Response**

A [ShipmentDTO](#shipmentdto) object

```

{
  "currency": "string",
  "name": "string",
  "price": 0,
  "status": "CREATED",
  "tariff": {
    "discount": 0,
    "name": "string",
    "rate": 0
  },
  "totalPrice": 0,
  "vehicle": {
    "available": true,
    "capacity": 0,
    "name": "string"
  },
  "weight": 0
}
```
---


### GET /shipments/
Gets All the recorded shipments' information

File: [ShipmentController.java](#project-structure)

**Parameters**

None

**Path Variable**

None

**Request Body**

None

**Response**

A list of [ShipmentDTO](#shipmentdto) object

```
[
   {
     "currency": "string",
     "name": "string",
     "price": 0,
     "status": "CREATED",
     "tariff": {
       "discount": 0,
       "name": "string",
       "rate": 0
     },
     "totalPrice": 0,
     "vehicle": {
       "available": true,
       "capacity": 0,
       "name": "string"
     },
     "weight": 0
   },
   ...
]
```
---

### GET /shipments/most-expensive
Gets the most expensive shipment with respect to its total price

File: [ShipmentController.java](#project-structure)

**Parameters**

None

**Path Variable**

None

**Request Body**

None

**Response**

A [ShipmentDTO](#shipmentdto) object

```
{
  "currency": "string",
  "name": "string",
  "price": 0,
  "status": "CREATED",
  "tariff": {
    "discount": 0,
    "name": "string",
    "rate": 0
  },
  "totalPrice": 0,
  "vehicle": {
    "available": true,
    "capacity": 0,
    "name": "string"
  },
  "weight": 0
}
```
---

### POST /shipments
Creates a new record of shipment in the database with unique name

File: [ShipmentController.java](#project-structure)

**Parameters**

None

**Path Variable**

None

**Request Body**

A [ShipmentDTO](#shipmentdto) object

**Response**

A list of [ShipmentDTO](#shipmentdto) object

```
{
  "currency": "string",
  "name": "string",
  "price": 0,
  "status": "CREATED",
  "tariff": {
    "discount": 0,
    "name": "string",
    "rate": 0
  },
  "totalPrice": 0,
  "vehicle": {
    "available": true,
    "capacity": 0,
    "name": "string"
  },
  "weight": 0
}
```
---

### PUT /shipments
Updates a record of shipment in the database with unique name

File: [ShipmentController.java](#project-structure)

**Parameters**

None

**Path Variable**

None

**Request Body**

A [ShipmentDTO](#shipmentdto) object

**Response**

A list of [ShipmentDTO](#shipmentdto) object

```
{
  "currency": "string",
  "name": "string",
  "price": 0,
  "status": "CREATED",
  "tariff": {
    "discount": 0,
    "name": "string",
    "rate": 0
  },
  "totalPrice": 0,
  "vehicle": {
    "available": true,
    "capacity": 0,
    "name": "string"
  },
  "weight": 0
}
```
---

### DELETE /shipments/{name}
Deletes a shipment from the database using its name

File: [ShipmentController.java](#project-structure)

**Parameters**

None

**Path Variable**

|   Name | Required |  Type  | Description          |
|-------:|:--------:|:------:|----------------------|
| `name` | required | String | name of the shipment |


**Request Body**

None


**Response**

None
---






### GET /tariffs/{name}
Gets the required information for a tariff using its name.

File: [TariffController.java](#project-structure)

**Parameters**

None

**Path Variable**

|   Name | Required |  Type  | Description          |
|-------:|:--------:|:------:|----------------------|
| `name` | required | String | name of the shipment |


**Request Body**

None


**Response**

A [TariffDTO](#tariffdto) object

```

{
  "discount": 0,
  "name": "string",
  "rate": 0,
  "vehicles": [
    {
      "available": true,
      "capacity": 0,
      "name": "string"
    }
  ]
}
```
---


### GET /tariffs/
Gets All the recorded tariffs' information

File: [TariffController.java](#project-structure)

**Parameters**

None

**Path Variable**

None

**Request Body**

None

**Response**

A list of [TariffDTO](#tariffdto) object

```
[
   {
     "discount": 0,
     "name": "string",
     "rate": 0,
     "vehicles": [
       {
         "available": true,
         "capacity": 0,
         "name": "string"
       }
     ]
   },
   ...
]
```
---


### POST /tariffs
Creates a new record of tariff in the database with unique name

File: [TariffController.java](#project-structure)

**Parameters**

None

**Path Variable**

None

**Request Body**

A [TariffDTO](#tariffdto) object

**Response**

A list of [TariffDTO](#tariffdto) object

```
{
  "discount": 0,
  "name": "string",
  "rate": 0,
  "vehicles": [
    {
      "available": true,
      "capacity": 0,
      "name": "string"
    }
  ]
}
```
---

### PUT /tariffs
Updates a record of tariff in the database using its unique name

File: [TariffController.java](#project-structure)

**Parameters**

None

**Path Variable**

None

**Request Body**

A [TariffDTO](#tariffdto) object

**Response**

A list of [TariffDTO](#tariffdto) object

```
{
  "discount": 0,
  "name": "string",
  "rate": 0,
  "vehicles": [
    {
      "available": true,
      "capacity": 0,
      "name": "string"
    }
  ]
}
```
---

### DELETE /tariffs/{name}
Deletes a tariff from the database using its name

File: [TariffController.java](#project-structure)

**Parameters**

None

**Path Variable**

|   Name | Required |  Type  | Description          |
|-------:|:--------:|:------:|----------------------|
| `name` | required | String | name of the shipment |


**Request Body**

None


**Response**

None

---




### GET /vehicles/{name}
Gets the required information for a vehicle using its name.

File: [VehicleController.java](#project-structure)

**Parameters**

None

**Path Variable**

|   Name | Required |  Type  | Description          |
|-------:|:--------:|:------:|----------------------|
| `name` | required | String | name of the shipment |


**Request Body**

None


**Response**

A [VehicleDTO](#vehicledto) object

```

{
  "available": true,
  "capacity": 0,
  "name": "string"
}
```
---

### GET /vehicles/
Gets All the recorded vehicles' information

File: [VehicleController.java](#project-structure)

**Parameters**

None

**Path Variable**

None

**Request Body**

None

**Response**

A list of [VehicleDTO](#vehicledto) object

```
[
   {
     "available": true,
     "capacity": 0,
     "name": "string"
   },
   ...
]
```
---


### POST /vehicles
Creates a new record of vehicle in the database with unique name

File: [VehicleController.java](#project-structure)

**Parameters**

None

**Path Variable**

None

**Request Body**

A [VehicleDTO](#vehicledto) object

**Response**

A list of [VehicleDTO](#vehicledto) object

```
{
  "available": true,
  "capacity": 0,
  "name": "string"
}
```
---


### PUT /vehicles
Updates a record of vehicle in the database using its unique name

File: [VehicleController.java](#project-structure)

**Parameters**

None

**Path Variable**

None

**Request Body**

A [VehicleDTO](#vehicledto) object

**Response**

A list of [VehicleDTO](#vehicledto) object

```
{
  "available": true,
  "capacity": 0,
  "name": "string"
}
```
---

### POST /vehicles/{vehicle}?available={available}
Sets the availability of the vehicle manually.

File: [VehicleController.java](#project-structure)

**Parameters**


|      Name | Required |  Type  | Description          |
|----------:|:--------:|:------:|----------------------|
| `vehicle` | required | String | name of the shipment |

**Path Variable**


|        Name | Required |  Type   | Description                 |
|------------:|:--------:|:-------:|-----------------------------|
| `available` | required | boolean | availability of the vehicle |

**Request Body**

None

**Response**

None (status 200)

---


### DELETE /vehicles/{name}
Deletes a vehicle from the database using its name

File: [VehicleController.java](#project-structure)

**Parameters**

None

**Path Variable**

|   Name | Required |  Type  | Description          |
|-------:|:--------:|:------:|----------------------|
| `name` | required | String | name of the shipment |


**Request Body**

None


**Response**

None (status 200)

---




### POST /assign/vehicle/{vehicle}/to/{shipment}
Assigns a vehicle to a shipment if the vehicle has the weight capacity for the shipment. If the shipment has a tariff assigned to it, the vehicle only gets assigned to shipment
if and only if the tariff's list of applicable vehicles contains the vehicle. If assigned a vehicle to shipment, the
vehicle's availability will become false. If there is a tariff already assigned to shipment, the total price will be calculated using formula
`(shipment-price * (1 + tariff-rate * (1 - tariff-discount)))`.

File: [AssignmentController.java](#project-structure)

**Parameters**

None

**Path Variable**

|       Name | Required |  Type  | Description          |
|-----------:|:--------:|:------:|----------------------|
|  `vehicle` | required | String | name of the vehicle  |
| `shipment` | required | String | name of the shipment |


**Request Body**

None


**Response**

None (status 200)

---


### POST /assign/tariff/{tariff}/to/{shipment}
Assigns a tariff to a shipment. If there is a vehicle already assigned to the shipment, tariff will only get assigned to the shipment
if and only if its list of applicable vehicles contain the vehicle assigned to the shipment. If assigned, the total price of the shipment will
be calculated using formula `(shipment-price * (1 + tariff-rate * (1 - tariff-discount)))`.

File: [AssignmentController.java](#project-structure)

**Parameters**

None

**Path Variable**

|       Name | Required |  Type  | Description          |
|-----------:|:--------:|:------:|----------------------|
|   `tariff` | required | String | name of the tariff   |
| `shipment` | required | String | name of the shipment |


**Request Body**

None


**Response**

None (status 200)

---

### POST /assign/to/{shipment}/auto
Assigns the cheapest tariff (with lowest rate and highest discount) and a vehicle with enough capacity (that is contained within the associated tariff's list of applicable
vehicles) to a shipment.

File: [AssignmentController.java](#project-structure)

**Parameters**

None

**Path Variable**

|       Name | Required |  Type  | Description          |
|-----------:|:--------:|:------:|----------------------|
| `shipment` | required | String | name of the shipment |


**Request Body**

None


**Response**

None (status 200)

---

### POST /assign/revoke/{shipment}
Revokes the tariff and vehicle assignment from a shipment and sets the vehicle availability to true and total price of the shipment to 0

File: [AssignmentController.java](#project-structure)

**Parameters**

None

**Path Variable**

|       Name | Required |  Type  | Description          |
|-----------:|:--------:|:------:|----------------------|
| `shipment` | required | String | name of the shipment |


**Request Body**

None


**Response**

None (status 200)

---

### POST /assign/tariff-vehicle/{vehicle}/to/{tariff}
Adds a vehicle to the list of applicable vehicles for a tariff

File: [AssignmentController.java](#project-structure)

**Parameters**

None

**Path Variable**

|      Name | Required |  Type  | Description         |
|----------:|:--------:|:------:|---------------------|
| `vehicle` | required | String | name of the vehicle |
|  `tariff` | required | String | name of the tariff |


**Request Body**

None


**Response**

None (status 200)

---

### POST /assign/remove/tariff-vehicle/{vehicle}/from/{tariff}
Removes a vehicle from the list of applicable vehicles for a tariff

File: [AssignmentController.java](#project-structure)

**Parameters**

None

**Path Variable**

|      Name | Required |  Type  | Description         |
|----------:|:--------:|:------:|---------------------|
| `vehicle` | required | String | name of the vehicle |
|  `tariff` | required | String | name of the tariff |


**Request Body**

None


**Response**

None (status 200)

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
    ├── src                     
    │   ├── main  
    │   │   ├── java
    │   │   │   └── com.elemica.api
    │   │   │       ├── config
    │   │   │       │   ├── ApplicationConfig.java
    │   │   │       │   ├── LoggingAspect.java
    │   │   │       │   └── SpringFoxConfig.java
    │   │   │       ├── controllers                            # Endpoints
    │   │   │       │   ├── AssignmentController.java          # a rest api for assignments   
    │   │   │       │   ├── ShipmentController.java            # Endpoints for shipments
    │   │   │       │   ├── TariffController.java              # Endpoints for tariffs
    │   │   │       │   └── VehicleController.java             # Endpoints for vehicles
    │   │   │       ├── models
    │   │   │       │   ├── enums
    │   │   │       │   │   └── ShipmentStatus.java
    │   │   │       │   ├── Shipment.java
    │   │   │       │   ├── Tariff.java
    │   │   │       │   └── Vehicle.java
    │   │   │       ├── repositories
    │   │   │       │   ├── ShipmentRepository.java
    │   │   │       │   ├── TariffRepository.java
    │   │   │       │   └── VehicleRepository.java
    │   │   │       ├── services
    │   │   │       │   ├── dtos
    │   │   │       │   │   ├── ShipmentDTO.java
    │   │   │       │   │   ├── TariffDTO.java
    │   │   │       │   │   └── VehicleDTO.java
    │   │   │       │   ├── exceptions
    │   │   │       │   │   ├── CannotAssignAutomaticallyException.java
    │   │   │       │   │   ├── NoVehicleWithCapacityException.java
    │   │   │       │   │   ├── ShipmentAlreadyExistsException.java
    │   │   │       │   │   ├── ShipmentNotExistException.java
    │   │   │       │   │   ├── TariffAlreadyExistsException.java
    │   │   │       │   │   ├── TariffDoesNotHaveVehicle.java
    │   │   │       │   │   ├── TariffNotExistException.java
    │   │   │       │   │   ├── VehicleAlreadyExistException.java
    │   │   │       │   │   ├── VehicleCapacityLimitException.java
    │   │   │       │   │   ├── VehicleNotAvailableException.java
    │   │   │       │   │   └── VehicleNotExistException.java
    │   │   │       │   ├── mappers
    │   │   │       │   │   ├── EntityMapper.java
    │   │   │       │   │   ├── ShipmentMapper.java
    │   │   │       │   │   ├── TariffMapper.java
    │   │   │       │   │   └── VehicleMapper.java
    │   │   │       │   ├── AssignmentService.java
    │   │   │       │   ├── IService.java
    │   │   │       │   ├── ShipmentService.java
    │   │   │       │   ├── TariffService.java
    │   │   │       │   └── VehicleService.java            
    │   │   │       └── ElemicaApplication.java
    │   │   └── resource
    │   │   │   ├── application.yml
    │   │   │   ├── application-dev.yml
    │   │   │   ├── application-docker.yml
    │   │   │   ├── application-prod.yml
    │   │   │   └── application-test.yml
    │   └── test 
    │       ├── java
    │       │    └── com.elemica.api
    │       │       ├── controllers                         
    │       │       │   ├── AssignmentControllerTest.java                
    │       │       │   ├── ShipmentControllerTest.java           
    │       │       │   ├── TariffControllerTest.java          
    │       │       │   └── VehicleControllerTest.java         
    │       │       ├── data
    │       │       │   └── TestDataLoader.java                # loads the test data
    │       │       ├── repositories
    │       │       │   ├── ShipmentRepositoryTest.java
    │       │       │   ├── TariffRepositoryTest.java
    │       │       │   └── VehicleRepositoryTest.java
    │       │       └── services
    │       │           ├── mappers
    │       │           │   ├── ShipmentMapperTest.java
    │       │           │   ├── TariffMapperTest.java
    │       │           │   └── VehicleMapperTest.java
    │       │           ├── AssignmentServiceTest.java              
    │       │           ├── ShipmentServiceTest.java              
    │       │           ├── TariffServiceTest.java              
    │       │           └── VehicleServiceTest.java
    │       │
    │       └── resources
    │           └── testset                                         # test sets for unit and integration testint
    │               ├── shipments.json              
    │               ├── shipments-assignment.json          
    │               ├── tariffs.json              
    │               ├── tariffs-assignment.json         
    │               ├── vehicles.json          
    │               └── vehicles-assignment.json     
    ├── .env                    # Docker compose environement variables 
    ├── docker-compose.yaml     # Docker compose file containing this web applicationa and postgres containers 
    ├── Dockerfile              # Dockerfile for creating image for the web application to start a container in docker
    ├── mvnw  
    ├── mvnw.cmd  
    ├── pom.xml
    ├── README.md
    └── ...

**Note**: You can check out project's documentation from ./documentation/index.html