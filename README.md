# ISSS - Student information system

## Endpoints

### Auth

#### Register

* POST: /api/v1/auth/register   

* JSON: {
        "name": "Name Surname",
        "email": "name@email.com",
        "password": "password1234",
        "birth": "2000-01-01",
        "address": {
            "streetName": "Street Name",
            "houseNumber": "25",
            "zipCode": 72200
        }
}

#### Login

* POST: /api/v1/auth/login  

* JSON: {
        "email": "name@email.com",
        "password": "password1234",
}


### Student

#### Get all students

* GET: /api/v1/student

#### Get single student

* GET: /api/v1/student/{id}

#### Edit student

* PUT: /api/v1/student/{id}  

* Requested params: email, name

#### Delete student

* DELETE: /api/v1/student/{id}  

### Address

#### Get all addresses

* GET: /api/v1/address

### City

#### Get all cities

* GET: /api/v1/city

#### Edit city

* POST: /api/v1/city
* JSON: {"name: "City"}

#### Delete city

* DELETE: /api/v1/city/{id} 



