#!/bin/bash
ENDPOINT=http://localhost:8000

# Delete Tournament Table
aws dynamodb delete-table  --endpoint-url $ENDPOINT --table-name tournament >/dev/null
aws dynamodb delete-table  --endpoint-url $ENDPOINT --table-name ingestmap1 >/dev/null
aws dynamodb delete-table  --endpoint-url $ENDPOINT --table-name ingestmap2 >/dev/null
aws dynamodb delete-table  --endpoint-url $ENDPOINT --table-name countrycodes >/dev/null

