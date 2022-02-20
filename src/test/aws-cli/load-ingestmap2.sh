#!/bin/bash
ENDPOINT=http://localhost:8000

while IFS= read -r line; do
  aws dynamodb put-item --endpoint-url $ENDPOINT \
    --table-name ingestmap2  \
    --item "$line";
done < ../json/ingestmap2.json
