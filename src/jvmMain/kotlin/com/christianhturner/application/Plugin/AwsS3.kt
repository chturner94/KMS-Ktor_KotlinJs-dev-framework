package com.christianhturner.plugins.AWSs3

import aws.sdk.kotlin.services.s3.*
import com.amazonaws.*
import com.amazonaws.auth.AWSStaticCredentialsProvider
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.regions.Regions
import com.amazonaws.services.s3.AmazonS3ClientBuilder
import io.ktor.serialization.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.util.*
import java.util.*

val awsS3Plugin = createApplicationPlugin(
    name = "awsS3Plugin",
    createConfiguration = ::AwsS3Configuration
) {

    class AwsS3Configuration(
        val accessKey: String,
        val secretKey: String,
        val region: Regions,
    ) {
        fun buildClient(accesKey: String, secretKey: String, region: Regions) {
            val awsCreds = BasicAWSCredentials(accessKey, secretKey)
            val s3Client = AmazonS3ClientBuilder.standard()
                .withCredentials(AWSStaticCredentialsProvider(awsCreds))
                .withRegion(region)
                .build()
        }
        }
}
    /*fun ApplicationCall.s3Client(): AmazonS3 {
        val awsS3Configuration = application.awsS3Configuration
        val awsCredentials = BasicAWSCredentials(awsS3Configuration.accessKey, awsS3Configuration.secretKey)
        val s3Client = AmazonS3ClientBuilder.standard()
            .withCredentials(StaticCredentialsProvider(awsCredentials))
            .withRegion(Regions.fromName(awsS3Configuration.region))
            .build()
        return s3Client
    }

    fun ApplicationCall.generatePresignedUrl(bucketName: String, key: String, expiration: LocalDateTime): String {
        val s3Client = s3Client()
        val expirationDate = Date.from(expiration.atZone(ZoneId.systemDefault()).toInstant())
        val generatePresignedUrl = GeneratePresignedUrlRequest(bucketName, key)
            .withMethod(HttpMethod.Get)
            .withExpiration(expirationDate)
        val url = s3Client.generatePresignedUrl(generatePresignedUrl)
        return url.toString()
    }

    fun ApplicationCall.uploadFile(
        bucketName: String,
        key: String,
        file: ByteArray,
        sseCustomerKey: SSECustomerKey? = null
    ) {
        val s3Client = s3Client()
        val uploadRequest = PutObjectRequest {
            bucket = bucketName
            key = key
            body = file
            sseCustomerKey?.let { sseCustomerKey = it }
        }
        s3Client.putObject(uploadRequest)
    }
}

class AwsS3Configuration(
    accessKey: String,
    secretKey: String,
    region: Regions
){
 //   val s3Client = AmazonS3ClientBuilder.standard()
   //     .withCredentials(BasicAWSCredentials(accessKey, secretKey))
     //   .withRegion(region)
       // .build()
}*/
/*
suspend fun getObjectFromS3(bucketName: String, keyName: String, path: String) {
    val s3Client = AmazonS3ClientBuilder.standard()
        .withRegion(Regions.US_EAST_1)
        .build()
    try {
        var expiration: Any = LocalDateTime.now().plusHours(1)
        val generatePresignedUrlRequest = GeneratePresignedUrlRequest(bucketName, keyName).apply {
            method = HttpMethod.GET
            expiration = Date.from(expiration.atZone(ZoneId.systemDefault()).toInstant())
        }
        SSECustomerKey.generateSSECustomerKeyForPresignUrl()

    } catch (e: ) {
        System.err.println(e.awsErrorDetails().errorMessage());
        exitProcess(1)
    } catch (e: SdkClientException) {
        System.err.println(e.message)
        exitProcess(1)
    }


    }

private fun S3Client.getObjectUrl(bucketName: String, keyName: String): String? {
    TODO("Not yet implemented")
}*/
