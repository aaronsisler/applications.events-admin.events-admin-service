#!/bin/sh
echo "Begin: DynamoDB -> Create Table"
awslocal dynamodb create-table \
    --cli-input-json file://dynamodb-table-definition.json \
    --region us-east-1
echo "End: DynamoDB -> Create Table"
#
echo "Begin: DynamoDB -> List Tables"
#
awslocal dynamodb list-tables
#
echo "End: DynamoDB -> List Tables"