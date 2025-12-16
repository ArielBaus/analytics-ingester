# Analytics Ingester

The Cyber Security Operations team wants to populate the Analytics Service by uploading
malicious activity logs (in csv format). This will be their primary way of ingesting data into the
analytical systems used for Incident Response. This makes it a mission-critical system.
In the refinement (when you were away), we decided that the best way to accomplish this is to:

1. Build a CLI application that will ingest the csv file.
   1. [Nice to Have] Allow CLI to include a parameter to filter the records that are to be
      ingested.
      b. Provide feedback to the CLI user.
2. Build a Microservice API project to process the records. It must have a single endpoint that
   will receive the call from the CLI application.
   1. For each record, the data needs to be enriched by calling the Enrichment Service
   (docs)
      1. Heads up - we have had intermittent issues with the Enrichment Service
      before. We are unsure if Canary Team has resolved their issue yet. Proceed
      with caution. 
   2. . The records must be sent to the Analytics Service (docs). 
      1. After the budget incident, this service is now rate limited at 20 messages
      per 10 seconds.

We use Authentication for both the Enrichment Service and the Analytics Service. Authentication
can be done via this HTTP header: Authorization: eye-am-hiring

## Technical and architectural decisions

- Project is setup using [Clean architecture](https://www.gregorypacheco.com.br/posts/differences-between-clean-architecture-and-hexagonal-architecture-cshrp-dot-net.html) design pattern to ensure distinct layers of abstraction are kept, this is enforced by archunit.
- Retryable annotation from spring resilience to allow for retries on enrichment calls.
- A single scheduled executor every 10 seconds will collect batches of up to 20 entries and post to analytics service

## Frameworks and libraries used

- Gradle is used for project build
- Spring Boot is used as the core framework for dependency injection
- Junit + Mockito + Wiremock are used for testing
- Library [system-lambda](https://github.com/stefanbirkner/system-lambda) is used in integration testing to ensure the output of the CLI into standard output

## How to compile and run the project

Project can be run with gradle through console terminal with the following commands
1) Start the API component that will receive the calls from CLI
2) Use CLI component

- How to start the API component
```
./gradlew clean build

java -jar ./build/libs/analytics_ingester-0.0.1-SNAPSHOT.jar
```

- How to use the CLI component
```
./gradlew clean build

java -jar -Dspring.main.web-application-type=NONE ./build/libs/analytics_ingester-0.0.1-SNAPSHOT.jar < ./src/main/resources/example_data.csv
```

## How to run the tests of the solution
Tests can be run either through an IDE like IntelliJ or with gradle through terminal with the following command

Example
```
./gradlew clean test
```
## Additional notes

Due to time constraints trade offs were made, the following features did not make it to being implemented
- Manual testing was done but most automated (unit/integration) tests are remaining to be done
- There is no user interface setup nor a `-help` command available explaining usage to CLI users
- No CSV format validations
- No API docs for web component
- No observability set up
- The function to allow CLI to include a parameter to filter the records that are to be
  ingested.