# Golf Tournament Data Ingestor

**Author**: Mark Wickens

**Date**: 20/02/2022

## Introduction

This document is laid out as per the project [Requirements](https://hackmd.io/aAPIP6lESR6Mz7SO6shpWg).

The source code is available in the GitHub repository [urbancamo/golf-tournament](https://github.com/urbancamo/golf-tournament).

Source code was written using IntelliJ IDEA running under Ubuntu. It is a Spring Boot project and requires a Java
11+ JDK installation (I'm using Amazon Coretto) and maven (this can be installed via `sudo apt-get install maven`).

The database is [Amazon DynamoDB](https://docs.aws.amazon.com/dynamodb/index.html).
Database creation scripts are written in `bash`, so if you are running this on Windows you will need a bash shell
interpreter.

Requires the [AWS Command Line Interface](https://aws.amazon.com/cli/) for database creation and population.

## Test coverage and tooling; integration and unit 

| Test Class                                | Purpose                                                    |
|-------------------------------------------|------------------------------------------------------------|
| `TournamentRestControllerIntegrationTest` | Checks the REST controller with mocked service layers      |
| `TournamentServiceIntegrationTest`        | Checks database persistence operations for Tournament Data |
| `CountryCodesDaoIntegrationTest`          | Checks the country codes lookup is working                 |
| `Iso2ToCountryTransformerUnitTest`        | Check conversion from Iso2 code to country name            |
| `EpochTransformerUnitTest`                | Check conversion beween epochs and dates                   |
| `Uk2DateTransformerUnitTest`              | Check conversion for 2 character year dates                |

Acknowledged that unit testing is thin on the ground and needs addressing!

Current tests have 100% class coverage, 77% Method coverage and 77% line coverage
(based on IntelliJ test coverage analyser)

## Readable and consistent code

The project is implemented as a Spring Boot Microservice. Internally the code follows a traditional n-tier pattern 
with Controllers, Service Layer, Persistence Layer and Domain Objects.

## Flexibility of the solution

The approach taken is to represent items in the source data as a Map of keys to string values. Classes
in the `transform` package are responsible for mapping both data item names and their representation before persisting.

This process is driven by data sourced from the `ingestmap1` and `ingestmap2` tables, corresponding to data source 1
and data source 2 respectively.

The Ingest Map tables contain the following data:

| Item        | Purpose                              | Sample Value   |
|-------------|--------------------------------------|----------------|
 | `key`       | Item name in the ingest data         | `tournamentId` |
| `value`     | Mapped name in the persistence stream | `sourceId`     |
| `transform` | Data transformation to apply| `uk2Date`      |

The `transform` field refers to the following transformations as provided in `uk.m0nom.golf.transform` - these classes 
implement the interface `Transform` and are created in the `TransformerFactory`.

| Classname                  | Transform Name   | Purpose                                                                     |
|----------------------------|------------------|-----------------------------------------------------------------------------|
| `CountryAlpha2Transformer` | `countryAlpha2 ` | Converts an ISO 2 character country code to country name                    |
| `EpochTransformer`         | `epoch`          | Converts a date specified as microseconds into internal format `dd/mm/yyyy` |
| `Uk2DateTransformer`       | `uk2Date`        | Converts a two digit year date into internal format `dd/mm/yyyy`            |

Adding a new data source, if the current transformers are sufficient, requires only a new ingest table to be 
populated. All data sources use the same API. It is a requirement of the data source provider that they specify
a custom header `source_id` to identify the source number.

A less flexible but cleaner looking implementation would have mapped java class member variables to data source 1 and
data source 2 data items. However, with this representation adding a new data source would have required a modification
to the application.

## Clear instructions for running and using your application

### Database Initialization

This implementation uses Amazon DynamoDB as the data store. The integration tests rely on a local instance of 
DyanmoDB to be installed and configured. 

Links at the bottom of this README provide information on how to install a local DynamoDB instance.

Bash scripts in the `src/main/test/aws-cli` directory are for database lifecycle operations:

| Script               | Purpose                                                               |
|----------------------|-----------------------------------------------------------------------|
| initialize-db.sh     | Creates the required tables in a local DynamoDB instance              |
| load-countrycodes.sh | Populates the countries table with data from `json/countryCodes.json` |
| load-ingestmap1.sh   | Populates the ingestmap1 table with data from `json/ingestmap1.json`  |
| load-ingestmap2.sh   | Populates the ingestmap2 table with data from `json/ingestmap2.json`  |
|run-dynamodb.sh| Runs a local DynamoDb instance|
|delete-tables.sh|Removes tables a data from the initialized database|

### Configuring the application

Parameters for the application and DynamoDB configuration are defined in `application.properties` to define the following:
- Default Server Port for REST API
- AWS Dynamo DB config - change if using AWS hosted version
- Transformer Configuration - defines the format of standard data representations

### Building the application

These examples assume the GIT repository [urbancamo/golf-tournament](https://github.com/urbancamo/golf-tournament) is
checked out into `~/Projects/golf-tournament`.

## Dependency selection and management

Dependencies are defined in `pom.xml` which requires [Apache Maven](https://maven.apache.org/) 
to be installed.

## Running

Database startup and population example script:

```
msw@gram:~/Projects/golf-tournament/src/test/aws-cli$ ./run-dynamodb.sh >/dev/null &
[1] 54574
msw@gram:~/Projects/golf-tournament/src/test/aws-cli$ ./delete-tables.sh 
msw@gram:~/Projects/golf-tournament/src/test/aws-cli$ ./initialize-db.sh 
msw@gram:~/Projects/golf-tournament/src/test/aws-cli$ ./load-ingestmap1.sh 
msw@gram:~/Projects/golf-tournament/src/test/aws-cli$ ./load-ingestmap2.sh 
msw@gram:~/Projects/golf-tournament/src/test/aws-cli$ ./load-countrycodes.sh 
msw@gram:~/Projects/golf-tournament/src/test/aws-cli$ ./list-tables.sh 
{
    "TableNames": [
        "countrycodes",
        "ingestmap1",
        "ingestmap2",
        "tournament"
    ]
}
msw@gram:~/Projects/golf-tournament/src/test/aws-cli$ 
```

Building the application: 

```
msw@gram:~/Projects/golf-tournament$ mvn clean package spring-boot:repackage
```

Running the application:

```
msw@gram:~/Projects/golf-tournament$ mvn spring-boot:run
```

There are sample http requests to exercise the REST API available in `src/test/http`:

### post-tournamentDs1.http:

```
http://localhost:5001/tournament/

HTTP/1.1 201 
Location: http://localhost:5001/tournament/429db6f6-d82d-472a-bc8e-7ee4e2e423ff
Content-Length: 0
Date: Sun, 20 Feb 2022 20:44:04 GMT
Keep-Alive: timeout=60
Connection: keep-alive

<Response body is empty>
```

### get-tournament.http:

```
http://localhost:5001/tournament/429db6f6-d82d-472a-bc8e-7ee4e2e423ff

HTTP/1.1 200 
Content-Type: application/json
Transfer-Encoding: chunked
Date: Sun, 20 Feb 2022 20:44:43 GMT
Keep-Alive: timeout=60
Connection: keep-alive

{
  "source": 1,
  "data": {
    "sourceId": "174638",
    "country": "United Kingdom",
    "name": "Women's Open Championship",
    "start": "09/07/2021",
    "course": "Sunnydale Golf Course",
    "forecast": "fair",
    "end": "13/07/2021",
    "source": "1",
    "id": "429db6f6-d82d-472a-bc8e-7ee4e2e423ff",
    "rounds": "4"
  },
  "id": "429db6f6-d82d-472a-bc8e-7ee4e2e423ff"
}
```

## Limitations

Limitations of the current implementation that would need consideration for production release:
 - Better Test Coverage.
 - No indexes for performant queries in the DynamoDB.
 - No authorisation/authentication/security - it is assumed these will be performed by an API Manager.
 - No data sanitization - sanitization is required for security purposes.
 - No data validation. Arbitrary key/value pairs in the source stream is a security risk and should be addressed. 
 - Move to typed storage of data items within the data streams, instead of treating all values as strings.
 - Performance has not been considered.
 - No consideration of uniqueness of data in the database.

## TODO

 - Integrate create, populate, run and teardown of DynamoDB inside Java Tests to provide idempotence.
 - Database table loads reference JSON files that are single line JSON snippets, rewrite table load scripts to use java based utilities.
 - Single table for all ingest data might be a better solution.

## Useful Links

- [Amazon DynamoDB](https://docs.aws.amazon.com/dynamodb/index.html)
- [SDK for Java V2](https://sdk.amazonaws.com/java/api/latest/software/amazon/awssdk/services/dynamodb/package-summary.html)
- [Amazon DynamoDB - Working with Items, Java](https://docs.aws.amazon.com/amazondynamodb/latest/developerguide/JavaDocumentAPIItemCRUD.html)
- [Getting Started with Java and DynamoDB](https://docs.aws.amazon.com/amazondynamodb/latest/developerguide/GettingStarted.Java.html)
