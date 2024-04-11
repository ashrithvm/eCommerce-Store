E-Store:  Puzzles
==============
A RESTful service built in Java 17 using Spring Boot that provides a backend for an e-commerce store that sells puzzles. 
The service provides endpoints for managing puzzles, accounts, carts, orders, custom puzzles, and gifts. 
A frontend client is expected to consume the API to provide a user interface for the store.
## Table of Contents

- [Team](#team)
- [Prerequisites](#prerequisites)
- [Running](#how-to-run-it)
- [Known bugs and disclaimers](#known-bugs-and-disclaimers)
- [Testing](#how-to-test-it)
- [Generating Design Doc pdf](#how-to-generate-the-design-documentation-pdf)
- [Setup/run/test](#how-to-setupruntest-program)
- [Using the API](#API)
  - [Json Type Annotations](#json-type-annotations)
    - [Puzzle](#puzzle)
    - [Account](#account)
    - [PuzzleCartItem](#puzzlecartitem)
    - [CustomPuzzle](#custompuzzle)
    - [Cart](#cart)
    - [Order](#order)
    - [Gift](#gift)
  - [Puzzles API](#puzzles-api)
    - [GET all Puzzles](#get-all-puzzles)
    - [Get a puzzle by id](#get-a-puzzle-by-id)
    - [Get a puzzle by partial name](#get-a-puzzle-by-partial-name)
    - [Creating a new Puzzle](#creating-a-new-puzzle)
    - [Updating a Puzzle](#updating-a-puzzle)
    - [Deleting a Puzzle](#deleting-a-puzzle)
  - [Accounts API](#accounts-api)
    - [Account Validation](#account-validation)
    - [Creating a new Account](#creating-a-new-account)
    - [Deleting an Account](#deleting-an-account)
  - [Cart API](#cart-api)
    - [Get a user's cart](#get-a-users-cart)
    - [Adding a Puzzle to the Cart](#adding-a-puzzle-to-the-cart)
    - [Updating the Cart](#updating-the-cart)
    - [Removing a Puzzle from the Cart](#removing-a-puzzle-from-the-cart)
  - [Orders API](#orders-api)
    - [Get all Orders](#get-all-orders)
    - [Get an Order by id](#get-an-order-by-id)
    - [Creating an Order](#creating-an-order)
  - [Custom Puzzles API](#custom-puzzles-api)
    - [Get all Custom Puzzles](#get-all-custom-puzzles)
    - [Get a Custom Puzzle by Account that Created it](#get-a-custom-puzzle-by-account-that-created-it)
    - [Creating a Custom Puzzle](#creating-a-custom-puzzle)
    - [Deleting a Custom Puzzle](#deleting-a-custom-puzzle)
  - [Gifts API](#gifts-api) 
    - [Send a gift email](#send-a-gift-email)
- [License](#license)


  
## Team

- Sejal Bhattad
- Klejdis Beshi
- Ashrith Mudundi
- Nick Ali
- Jon Rodriguez


## Prerequisites

- Java 17 (Make sure to have correct JAVA_HOME setup in your environment)
- Maven

## How to run it

1. Clone the repository and go to the root directory.
2. Execute `mvn compile exec:java` or `mvn spring-boot:run`
3. Refer to [API Usage](#API) to see what endpoints are available and how to use them.

## Known bugs and disclaimers
Nothing we are aware of currently.

## How to test it

The Maven build script provides hooks for run unit tests and generate code coverage
reports in HTML.

To run tests on all tiers together do this:

1. Execute `mvn clean test jacoco:report`
2. Open in your browser the file at `PROJECT_API_HOME/target/site/jacoco/index.html`

To run tests on a single tier do this:

1. Execute `mvn clean test-compile surefire:test@tier jacoco:report@tier` where `tier` is one of `controller`, `model`, `persistence`
2. Open in your browser the file at `PROJECT_API_HOME/target/site/jacoco/{controller, model, persistence}/index.html`

To run tests on all the tiers in isolation do this:

1. Execute `mvn exec:exec@tests-and-coverage`
2. To view the Controller tier tests open in your browser the file at `PROJECT_API_HOME/target/site/jacoco/model/index.html`
3. To view the Model tier tests open in your browser the file at `PROJECT_API_HOME/target/site/jacoco/model/index.html`
4. To view the Persistence tier tests open in your browser the file at `PROJECT_API_HOME/target/site/jacoco/model/index.html`

*(Consider using `mvn clean verify` to attest you have reached the target threshold for coverage)
  
  
## How to generate the Design documentation PDF

1. Access the `PROJECT_DOCS_HOME/` directory
2. Execute `mvn exec:exec@docs`
3. The generated PDF will be in `PROJECT_DOCS_HOME/` directory


## How to setup/run/test program 
1. Tester, first obtain the Acceptance Test plan
2. IP address of target machine running the app
3. Execute ________
4. ...
5. ...



<a name="API"></a>
# Using the API
The REST backend sends responses in the form of JSON.
## Json Type Annotations
### Puzzle
```angular2html
{
    "id": int,
    "name": string,
    "quantity": int,
    "description": string,
    "price": double,
    "difficulty": string,
    "imageURL": string
}

```
### Account
```
{
    "id": int,
    "username": string
}
```
Account with id=1 and username="admin" is reserved for the admin user.

### PuzzleCartItem
[Puzzle](#Puzzle) reference.
``` angular2html
{
    "puzzle": Puzzle,
    "quantity": int
}
```

### CustomPuzzle
[Account](#Account) reference.
``` angular2html
{
    "id": int,
    "account": Account,
    "quantity": int,
    "price": double,
    "difficulty": string,
    "imageURL": string,
}
```

### Cart
#### References
* [Puzzle](#Puzzle)
* [PuzzleCartItem](#PuzzleCartItem)
* [CustomPuzzle](#CustomPuzzle)
``` angular2html
{
    "id": int,
    "items": PuzzleCartItem[],
    "customPuzzles": CustomPuzzle[]
}
```

### Order
#### References
* [Puzzle](#Puzzle)
* [PuzzleCartItem](#PuzzleCartItem)
* [CustomPuzzle](#CustomPuzzle)

``` angular2html
{
    "id": int,
    "buyer": Account,
    "items": PuzzleCartItem[],
    "customPuzzles": CustomPuzzle[],
    "total": double
}
```

### Gift
[Order](#Order) reference

``` angular2html
{
    "id": int,
    "order": Order,
    "recipientEmail": string,
    "message": string
}
```
## Puzzles API
### GET all Puzzles 
GET request to http://localhost:8080/puzzles
<a name="getPuzzle"></a>
### Get a puzzle by id
Get request to http://localhost:8080/puzzles/{id}. \
**Example**: To get puzzle with id=16, make a GET request to http://localhost:8080/puzzles/16.
404 Not Found if id does not exist.
### Get a puzzle by partial name 
http://localhost:8080/puzzles/?name={containsText}. This returns all the puzzles that contain the given string.
**Example**: If you want to get all the puzzles that contain 'p' in their name, you make a GET request to http://localhost:8080/puzzles/?name=n 
Empty body returned if nothing contains the string passed.
### Creating a new Puzzle 
POST Request to http://localhost:8080/puzzles with a JSON body in the format:
```
{
    "id": {id: int},
    "name": {name: string},
    "quantity": {quantity: int},
    "description": {description: string},
    "price": {price: double},
    "difficulty": {difficulty},   Can be: "EASY", "MEDIUM", "HARD"
    "imageURL": {imageURL: string}
}
```
**Example**:
```
{
    "id": 1,
    "name": "NYC",
    "quantity": 8,
    "description": "Puzzle of the empire state tower.",
    "price": 124.99,
    "difficulty": "MEDIUM",
    "imageURL": "nyc.jpg"
}
```

### Updating a Puzzle
PUT request to http://localhost:8080/puzzles with a JSON body in the same fashion as for creating. The id has to match an existing record and update any other field as needed.
If the id is not found, "404" status code is returned.
### Deleting a Puzzle
DELETE request to http://localhost:8080/puzzles/{id}. 404 Not Found if the id does not exist.
## Accounts API
### Account Validation
To have the server validate the user, make a GET request to http://localhost:8080/accounts/validate/?username={username}&token={token}.
A token is a key that gives you access to this restricted API. \
If the username is valid, the server returns a 200 OK status code and 
the account information in the form of a JSON object. \
**Example**: To validate the user with username="user", make a GET request to http://localhost:8080/accounts/validate/?username=user&token=1234. \
Note: 1234 is not a valid token, it is just an example. The token is provided when you get access to the API.
### Creating a new Account
POST request to http://localhost:8080/accounts/?token={token} with a JSON body in the format:
```
{
    "id": {id: int},
    "username": {username: string},
}
```
**Example**:
```
{
    "id": 1,
    "username": "user"
}
```
If the id or username exist, a 409 conflict status code is returned.
The token for editing permission is different from the token for validation.
### Deleting an Account
DELETE request to http://localhost:8080/accounts/{id}?token={token}. 404 Not Found if the id does not exist.
The token is the same as the one used for creating the account.
## Cart API
### Get a user's cart
First, the admin user has no cart. Every other user has a cart that corresponds to them, created when the user is created.
To get it, make a GET request to http://localhost:8080/carts/{accountId}. The server will return a JSON object in this format:
```
{
"id": int
"puzzles": [
  {
  "id": int,
  "quantity": int
  }]
}
```
Let's explain what this is. The first id corresponds to the user the cart belongs  to. The second field puzzles is what stores the puzzles that are in the cart. For every puzzle, the puzzleId, referred to as `id`, is stored. To get the all the details about the puzzle, make another requhttp://localhost:8080/cart/{accountId}/add?puzzleId={puzzleId}est to the server, if unsure how to do it, go to: [Getting puzzle by id](#getPuzzle). The `quantity` field represents the quantity of this specific puzzle in the cart. A user can add the same product in the cart more than once, that is why the quantity field is needed.

### Adding a Puzzle to the Cart
To add a puzzle to the cart, make a POST request to http://localhost:8080/cart/{accountId}/add?puzzleId={puzzleId} \
**Example**: 
To add Puzzle with id = 3 to the cart of the user whose id = 10, do `http://localhost:8080/cart/10/add?puzzleId=3`. \
If a puzzle is already present in the cart of a user and a request to add it is made again, the quantity is incremented instead of adding the product as a duplicate.

### Updating the Cart
If you already know the id of the cart object and it exists, then you can make a PUT request to http://localhost:8080/cart/{accountId}/update with a JSON body of the form:
```
{
"id": int
"puzzles": [
  {
  "id": int,
  "quantity": int
  }]
}
```
The server will update the cart of the user if the user exists at all. If the user with that id does not exist, a `BAD REQUEST` HTTP error is returned. On success, the server returns the updated Cart.

### Removing a Puzzle from the Cart
To entirely remove a puzzle from the cart, make a DELETE request to http://localhost:8080/cart/{accountId}/remove?puzzleId={puzzleId}. Keep in mind that this removes the entire Puzzle record from the cart, it does not just decrement its quantity. 

## Orders API
### Get all Orders
To get all the orders, make a GET request to http://localhost:8080/orders. 
The server will return a list of JSON objects.
Refer to [Order](#Order) for the JSON object format.

### Get an Order by id
To get an order by the buyer's id, make a GET request to http://localhost:8080/orders/{accountId}.
The server will return a JSON object.
Refer to [Order](#Order) for the JSON object format.

### Creating an Order
To create an order, make a POST request to http://localhost:8080/orders.
The JSON body should be in the same format as [Order](#Order) 

**Example**

```
{
    "id": 1,
    "buyer": {
        "id": 1,
        "username": "user"
    },
    "items": [
        {
            "puzzle": {
                "id": 1,
                "name": "NYC",
                "quantity": 8,
                "description": "Puzzle of the empire state tower.",
                "price": 124.99,
                "difficulty": "MEDIUM",
                "imageURL": "nyc.jpg"
            },
            "quantity": 1
        }
    ],
    "customPuzzles": [
    {
            "id": 1,
            "account": {
                "id": 2,
                "username": "user"
            },
            "quantity": 1,
            "price": 124.99,
            "difficulty": "MEDIUM",
            "imageURL": "nyc.jpg"
        }
    ],
    "total": 124.99
}
```
The id that is passed does not matter, as the server generates an appropriate id
and returns the entire object with the updated id as part
of the HTTP response. The total is also re-calculated by the server.

Caution: Requests with a buyer whose id is 1 will be rejected as id 1 is reserved for the admin user.

## Custom Puzzles API
### Get all Custom Puzzles
To get all the custom puzzles, make a GET request to http://localhost:8080/customPuzzles.
The server will return a list of JSON objects. Refer to [CustomPuzzle](#CustomPuzzle) to verify what the list
of JSON objects will look like.

### Get a Custom Puzzle by Account that Created it
To get a custom puzzle by the account that created it, make a GET request to http://localhost:8080/customPuzzles/{accountId}.
The server will return a JSON object. Refer to [CustomPuzzle](#CustomPuzzle) to verify what the JSON object will look like.

### Creating a Custom Puzzle
To create a custom puzzle, make a POST request to http://localhost:8080/customPuzzles.
The JSON body should be in the same format as [CustomPuzzle](#CustomPuzzle)

**Example**

```
{
    "id": 1,
    "account": {
        "id": 2,
        "username": "user"
    },
    "quantity": 1,
    "price": 124.99,
    "difficulty": "MEDIUM",
    "imageURL": "nyc.jpg"
}
```
The id that is passed does not matter, as the server generates an appropriate id
and returns the entire object with the updated id as part of the HTTP response.
When the object is created, it is also added to the cart, in the customPuzzles list.

### Deleting a Custom Puzzle
To delete a custom puzzle, make a DELETE request to http://localhost:8080/customPuzzles/{id}.
The server will return a 404 Not Found if the id does not exist.
If the id exists, the server will return a 200 OK status code and the deleted [CustomPuzzle](#CustomPuzzle) object.

## Gifts API
Users can gift puzzles to other users. The buyer should provide
the recipient's email and a message to go along with the gift.

### Send a gift email
To send a gift email, make a POST request to http://localhost:8080/gifts.
The JSON body must contain a [Gift](#Gift) object.

## License

MIT License

See LICENSE for details.
