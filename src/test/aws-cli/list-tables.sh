#!/bin/bash
ENDPOINT=http://localhost:8000

aws dynamodb list-tables --endpoint-url $ENDPOINT
