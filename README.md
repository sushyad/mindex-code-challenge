# Mindex Coding Challenge
## What's Provided
A simple [Spring Boot](https://projects.spring.io/spring-boot/) web application has been created and bootstrapped 
with data. The application contains information about all employees at a company. On application start-up, an in-memory 
Mongo database is bootstrapped with a serialized snapshot of the database. While the application runs, the data may be
accessed and mutated in the database without impacting the snapshot.

### How to Run
The application may be executed by running `gradle bootRun`.

### How to Use
The following endpoints are available to use:
```
* CREATE
    * HTTP Method: POST 
    * URL: localhost:8080/employee
    * PAYLOAD: Employee
    * RESPONSE: Employee
* READ
    * HTTP Method: GET 
    * URL: localhost:8080/employee/{id}
    * RESPONSE: Employee
* UPDATE
    * HTTP Method: PUT 
    * URL: localhost:8080/employee/{id}
    * PAYLOAD: Employee
    * RESPONSE: Employee
```
The Employee has a JSON schema of:
```json
{
  "type":"Employee",
  "properties": {
    "employeeId": {
      "type": "string"
    },
    "firstName": {
      "type": "string"
    },
    "lastName": {
          "type": "string"
    },
    "position": {
          "type": "string"
    },
    "department": {
          "type": "string"
    },
    "directReports": {
      "type": "array",
      "items" : "string"
    }
  }
}
```
For all endpoints that require an "id" in the URL, this is the "employeeId" field.

## What to Implement
Clone or download the repository, do not fork it.

### Task 1
Create a new type, ReportingStructure, that has the following JSON schema:
```json
{
  "type":"ReportingStructure",
  "properties": {
    "employee": {
      "type": "employee"
    },
    "numberOfReports": {
      "type": "integer"
    }
    }
  }
}
```
For the field "numberOfReports", this should equal the total number of reports under a given employee. The number of 
reports is determined to be the number of directReports for an employee and all of their direct reports. For example, 
given the following employee structure:
```
                    John Lennon
                /               \
         Paul McCartney         Ringo Starr
                               /        \
                          Pete Best     George Harrison
```
The numberOfReports for employee John Lennon (employeeId: 16a596ae-edd3-4847-99fe-c4518e82c86f) would be equal to 4. 

This new type should have a new REST endpoint created for it. This new endpoint should accept an employeeId and return 
the fully filled out ReportingStructure for the specified employeeId. The values should be computed on the fly and will 
not be persisted.

### Task 2
Create a new type, Compensation. A Compensation has the following JSON schema:
```json
{
  "type":"Compensation",
  "properties": {
    "employee": {
      "type": "Employee"
    },
    "salary": {
          "type": "integer"
    },
    "effectiveDate": {
      "type": "string",
      "format": "date-time"
    }
    }
  }
}
```
Create two new Compensation REST endpoints. One to create and one to read by employeeId. These should persist and query the Compensation
from the in-memory persistence.

## Susheel's implementation notes:
I decided to make these sub-resources under an employee instead of standalone urls since they only makes sense in the context of an employee

### Task 1:
#### Get ReportingStructure
Method: Get
URL: http://localhost:8080/employee/16a596ae-edd3-4847-99fe-c4518e82c86f/reportingStructure
Response:
```json
{
    "employee": {
        "employeeId": "16a596ae-edd3-4847-99fe-c4518e82c86f",
        "firstName": "John",
        "lastName": "Lennon",
        "position": "Development Manager",
        "department": "Engineering",
        "directReports": [{
            "employeeId": "b7839309-3348-463b-a7e3-5de1c168beb3",
            "firstName": null,
            "lastName": null,
            "position": null,
            "department": null,
            "directReports": null
        }, {
            "employeeId": "03aa1462-ffa9-4978-901b-7c001562cf6f",
            "firstName": null,
            "lastName": null,
            "position": null,
            "department": null,
            "directReports": null
        }]
    },
    "numberOfReports": 4
}
```

### Task 2:
My implementation supports multiple compensation records for a given employee and a list of compensation records is returned.
To Do: Make employeeId and effectiveDate unique

#### Create Compensation

Method: POST
URL: http://localhost:8080/employee/16a596ae-edd3-4847-99fe-c4518e82c86f/compensation
Request Body:
```json
{
  "salary": "80000", 
  "effectiveDate": "2016-11-20T05:00:00Z"
}
```

#### Get Compensation
If the record does not exist, a 404 is returned, otherwise a list of compensations is returned for an employee

Method: GET
URL: http://localhost:8080/employee/16a596ae-edd3-4847-99fe-c4518e82c86f/compensation
Response:
```json
[{
    "employee": {
        "employeeId": "16a596ae-edd3-4847-99fe-c4518e82c86f",
        "firstName": null,
        "lastName": null,
        "position": null,
        "department": null,
        "directReports": null
    },
    "salary": 100000,
    "effectiveDate": "2018-11-20T05:00:00Z"
}, {
    "employee": {
        "employeeId": "16a596ae-edd3-4847-99fe-c4518e82c86f",
        "firstName": null,
        "lastName": null,
        "position": null,
        "department": null,
        "directReports": null
    },
    "salary": 90000,
    "effectiveDate": "2017-11-20T05:00:00Z"
}, {
    "employee": {
        "employeeId": "16a596ae-edd3-4847-99fe-c4518e82c86f",
        "firstName": null,
        "lastName": null,
        "position": null,
        "department": null,
        "directReports": null
    },
    "salary": 80000,
    "effectiveDate": "2016-11-20T05:00:00Z"
}]
```
