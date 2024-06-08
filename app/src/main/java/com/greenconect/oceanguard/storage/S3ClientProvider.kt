package com.greenconect.oceanguard.storage

import software.amazon.awssdk.auth.credentials.AwsSessionCredentials
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.S3Configuration
import java.net.URI

//import software.amazon.awssdk.utils.SdkAutoConstructMap
//S3ID: 9b5b54ca4b9badee1267c3ffc3db567f
//S3SECRETA: 6d72192c91a6d9aad634c08677b643bb8fbc1465cf97c5c073ea1842e8268a2e
//REGIÃO: us-west-1
// https://vdztvideesyjyuftcixg.supabase.co/storage/v1/s3

object S3ClientProvider {
    fun getS3Client(sessionToken: String): S3Client {
        val credentials = AwsSessionCredentials.create(
            "9b5b54ca4b9badee1267c3ffc3db567f",
            "6d72192c91a6d9aad634c08677b643bb8fbc1465cf97c5c073ea1842e8268a2e",
            sessionToken
        )

        return S3Client.builder()
            .region(Region.of("us-west-1"))
            .endpointOverride(URI.create("https://vdztvideesyjyuftcixg.supabase.co/storage/v1/s3"))
            .credentialsProvider(StaticCredentialsProvider.create(credentials))
            .serviceConfiguration(S3Configuration.builder()
                .pathStyleAccessEnabled(true)
                .build())
            .build()
    }
}
