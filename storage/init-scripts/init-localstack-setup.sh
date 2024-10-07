#!/bin/sh
echo "Begin: DynamoDB -> Create Table"
#
awslocal dynamodb create-table \
    --cli-input-json file://dynamodb-table-definition.json \
    --region us-east-1
#
echo "End: DynamoDB -> Create Table"
#
echo "Begin: DynamoDB -> List Tables"
#
awslocal dynamodb list-tables
#
echo "End: DynamoDB -> List Tables"
#
echo "Begin: S3 -> Make Bucket"
#
awslocal s3 mb \
    s3://event-admin-service-file-storage \
    --region us-east-1
#
echo "End: S3 -> Make Bucket"
#
echo "Begin: S3 -> List Buckets"
#
awslocal s3 ls
#
echo "End: S3 -> List Buckets"
