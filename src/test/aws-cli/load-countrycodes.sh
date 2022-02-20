#!/bin/bash
ENDPOINT=http://localhost:8000

while IFS= read -r line; do
  aws dynamodb put-item --endpoint-url $ENDPOINT \
    --table-name countrycodes  \
    --item "$line";
done < ../json/countryCodes.json
