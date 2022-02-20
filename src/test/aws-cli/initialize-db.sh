#!/bin/bash
ENDPOINT=http://localhost:8000

# Tournament Storage Table
aws dynamodb create-table --endpoint-url $ENDPOINT \
    --table-name tournament \
    --attribute-definitions \
        AttributeName=id,AttributeType=S \
    --key-schema AttributeName=id,KeyType=HASH \
    --provisioned-throughput ReadCapacityUnits=1,WriteCapacityUnits=1 \
    --table-class STANDARD > /dev/null

# Tournament Source 1 Mapping Table
aws dynamodb create-table --endpoint-url $ENDPOINT  \
    --table-name ingestmap1 \
    --attribute-definitions \
        AttributeName=key,AttributeType=S \
    --key-schema AttributeName=key,KeyType=HASH \
    --provisioned-throughput ReadCapacityUnits=1,WriteCapacityUnits=1 \
    --table-class STANDARD > /dev/null

# Tournament Source 2 Mapping Table
aws dynamodb create-table --endpoint-url $ENDPOINT  \
    --table-name ingestmap2 \
    --attribute-definitions \
        AttributeName=key,AttributeType=S \
    --key-schema AttributeName=key,KeyType=HASH \
    --provisioned-throughput ReadCapacityUnits=1,WriteCapacityUnits=1 \
    --table-class STANDARD > /dev/null

# Country Codes Storage Table
aws dynamodb create-table --endpoint-url $ENDPOINT \
    --table-name countrycodes \
    --attribute-definitions \
        AttributeName=Code,AttributeType=S \
    --key-schema AttributeName=Code,KeyType=HASH \
    --provisioned-throughput ReadCapacityUnits=1,WriteCapacityUnits=1 \
    --table-class STANDARD > /dev/null
