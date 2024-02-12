aws configure set aws_access_key_id "access_key_id"
aws configure set aws_secret_access_key "secret_access_key"
aws configure set region "us-east-1"

aws dynamodb batch-write-item \
  --endpoint-url=http://database:8000 \
  --request-items file:///data/10_clients.json

aws dynamodb batch-write-item \
  --endpoint-url=http://database:8000 \
  --request-items file:///data/20_locations.json

aws dynamodb batch-write-item \
  --endpoint-url=http://database:8000 \
  --request-items file:///data/30_organizers.json

aws dynamodb batch-write-item \
  --endpoint-url=http://database:8000 \
  --request-items file:///data/40_events.json

aws dynamodb batch-write-item \
  --endpoint-url=http://database:8000 \
  --request-items file:///data/50_event_schedules.json

aws dynamodb batch-write-item \
  --endpoint-url=http://database:8000 \
  --request-items file:///data/61_scheduled_events_get.json

aws dynamodb batch-write-item \
  --endpoint-url=http://database:8000 \
  --request-items file:///data/62_scheduled_events_get_all.json

aws dynamodb batch-write-item \
  --endpoint-url=http://database:8000 \
  --request-items file:///data/63_scheduled_events_delete.json

aws dynamodb batch-write-item \
  --endpoint-url=http://database:8000 \
  --request-items file:///data/64_scheduled_events_update.json
