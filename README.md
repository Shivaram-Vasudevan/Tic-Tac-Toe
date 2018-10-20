# Tic-Tac-Toe
A simple Tic Tac Toe game built with React and Spring Boot

## Setting up frontend
Navigate to the frontend folder and install all the dependencies required for the frontend
```
npm install
```

## Starting frontend
```
npm start
```

## Starting the Spring Boot application
1. Import the service folder in Eclipse or any IDE as a Maven project and run it

2. Build and run the service project using maven
```
mvn clean package -DskipTests
mvn spring-boot:run
```
To remote debug the service
```
mvn spring-boot:run 
    -Dspring-boot.run.jvmArguments="-Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=y,address=<port number of your choice>"
```
