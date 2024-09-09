# Spring Boot with Kafta & JWT

- Appropriate Flow for User Login with JWT
- Spring Boot Rest Api Architecture with Spring Security
- CRUD Operation for on Entity Book
- Asynchronous Processing - Kakfa Producer and consumer Implementation
- In memory user Authentication

## How to setup
1. Clone spring project repository
2. [Download](https://kafka.apache.org/quickstart) the latest Kafka release and extract it
3. Run
```
cd kafka-3.5.1-src
```
# Start the ZooKeeper service
```
$ .\bin\windows\zookeeper-server-start.bat .\config\zookeeper.properties
```
# Start the Kafka broker service
```
$ .\bin\windows\kafka-server-start.bat .\config\server.properties
```
4. if gradle not setup
```
./gradlew
```
   If not work, change file name from gradlew.sh and try
```
./gradlew.sh
```
5. Open spring repository in IntelliJ or Eclipse
6. Build with project the help of Maven
7. Run the application

## Rest API
REST API:
1. Login
    POST /rest/auth/login (admin/12345)
3. Created Authorized endpoints for the Book entity:
    GET /rest/api/books - Retrieve all books.
    GET /rest/api/books/{id} - Retrieve a book by its ID.
    POST /rest/api/books - Create a new book.
    PUT /rest/api/books/{id} - Update an existing book.
    DELETE /rest/api/books/{id} - Delete a book.

## Rest API test Using PostMan
Test case provided:
1. Book Repository
2. Book Service
3. API Endpoints

## Rest API test Using PostMan
Uploaded postman json to test APIs using Postman
