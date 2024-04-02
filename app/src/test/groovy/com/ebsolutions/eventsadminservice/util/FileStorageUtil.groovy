package com.ebsolutions.eventsadminservice.util

import com.ebsolutions.eventsadminservice.config.Constants
import jakarta.inject.Inject
import software.amazon.awssdk.core.ResponseInputStream
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.model.GetObjectRequest
import software.amazon.awssdk.services.s3.model.GetObjectResponse

import java.util.stream.Collectors

class FileStorageUtil {
    @Inject
    private S3Client s3Client

    List<String> getFileContents(String filePath) {
        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(Constants.FILE_STORAGE_LOCATION)
                .key(filePath)
                .build()
        println(filePath)
        ResponseInputStream<GetObjectResponse> s3objectResponse = s3Client
                .getObject(getObjectRequest)
        BufferedReader reader = new BufferedReader(new InputStreamReader(s3objectResponse))
        return reader.lines().collect(Collectors.toList())
    }
}
