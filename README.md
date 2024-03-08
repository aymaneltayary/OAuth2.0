# OAuth 2.0

Spring Security OAuth2 Boot simplifies standing up an OAuth 2.0 Authorization Server.This is an Authorization Server project based on java spring boot and mysql DB . This repository can be deployed as microservice and can be a part of any microservice architecture to give all other microservices the authorization required to access
It is an application that issues tokens for authorization.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment for notes on how to deploy the project on a live system.

### Prerequisites

MYSQL DB running locally

JDK 8 or higher version

Java IDE for example eclipse

Port 9999 is available on your machine



### Prerequisites



Execute the SQL file attached with the repository , the file is db.sql

From eclipse clone the repository  https://github.com/aymaneltayary/OAuth2.0.git into your workspace

After repsository clone import the project into your workspace

click on oauth-srv --> maven--> update project . You could need to check force update option

click on oauth-srv -- run Spring Boot App or Java Application


## Running the tests

NA

### Running the application

Once application starts up the autorization server is now ready to give you a token based on you acccess credetials .
Below are the URL expose by this microservice 

http://localhost:9999/oauth/token

http://localhost:9999/oauth/check_token

http://localhost:9999/oauth/token_key

Also , the full details of these request are availabe in oauth2.postman_collection.json included 



## Deployment

This app can be deployed locally
This app can be deployed as an image on docker hub through:
com.spotify maven dependency
Dockerfile included 

## Built With

* [Maven](https://maven.apache.org/) - Dependency Management

## Contributing

NA

## Versioning

0.0.1-SNAPSHOT 

## Authors

aeltayary@dxc.com

Ayman.eltayary@dxc.com


## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details

## Acknowledgments

