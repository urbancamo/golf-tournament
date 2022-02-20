#!/bin/bash
ENDPOINT=http://localhost:8000

while IFS= read -r line; do
  aws dynamodb put-item --endpoint-url $ENDPOINT \
    --table-name ingestmap1  \
    --item "$line";
done < ../json/ingestmap1.json
