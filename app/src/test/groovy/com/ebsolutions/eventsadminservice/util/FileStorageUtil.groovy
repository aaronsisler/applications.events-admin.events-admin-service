package com.ebsolutions.eventsadminservice.util


import jakarta.inject.Inject
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.model.ListBucketsResponse

class FileStorageUtil {
    @Inject
    private S3Client s3Client

    String getFile(String filePath) {
        ListBucketsResponse response = s3Client.listBuckets()
        println(response.buckets().get(0).name())
        println(filePath)
        return ""
    }
}
