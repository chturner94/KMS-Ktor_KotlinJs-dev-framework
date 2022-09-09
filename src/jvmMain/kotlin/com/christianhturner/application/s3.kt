package com.christianhturner.application

import aws.sdk.kotlin.runtime.auth.credentials.*
import com.christianhturner.application.Plugin.*
import io.ktor.server.application.*

import aws.sdk.kotlin.runtime.auth.credentials.ProfileCredentialsProvider
import aws.sdk.kotlin.services.s3.*
import com.amazonaws.*
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest
import com.amazonaws.AmazonServiceException;
import com.amazonaws.HttpMethod;
import com.amazonaws.SdkClientException;
import com.amazonaws.auth.AWSCredentialsProvider
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.SSECustomerKey
import com.christianhturner.plugins.AWSs3
import io.github.cdimascio.dotenv.dotenv

fun Application.configureS3() {
    install(awsS3Plugin)
    val dotenv = dotenv()
    val accessKey = dotenv["AWS_ACCESS_KEY_ID"]
    val secretKey = dotenv["AWS_SECRET_ACCESS_KEY"]
    val region = dotenv["AWS_REGION"]
    val bucketName = dotenv["AWS_BUCKET_NAME"]
    val s3 = AWSs3(accessKey, secretKey, region, bucketName)
    install(s3)
}
class s3Client : AwsS3Configuration {
    override val accessKey: String = dotenv()["AWS_ACCESS"]
}