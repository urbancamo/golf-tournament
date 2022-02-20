#!/bin/bash
CWD="${pwd}"
DYNAMODB_HOME=~/Projects/DynamoDBLocal/
cd $DYNAMODB_HOME
java -Djava.library.path=./DynamoDBLocal_lib -jar DynamoDBLocal.jar -sharedDb
cd "$CWD"
