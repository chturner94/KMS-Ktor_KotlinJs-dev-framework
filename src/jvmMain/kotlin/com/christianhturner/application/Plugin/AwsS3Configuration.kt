package com.christianhturner.application.Plugin

import aws.sdk.kotlin.services.s3.S3Client
import aws.sdk.kotlin.services.s3.model.GetObjectRequest
import aws.smithy.kotlin.runtime.content.writeToFile
import com.amazonaws.auth.AWSCredentials
import com.amazonaws.auth.AWSStaticCredentialsProvider
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.regions.Regions
import com.amazonaws.services.*
import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.AmazonS3Client
import com.amazonaws.services.s3.AmazonS3ClientBuilder
import com.christianhturner.application.*
import io.ktor.http.ContentDisposition.Companion.File
import io.ktor.server.application.*
import java.io.File
import java.util.*
import kotlin.reflect.full.createInstance

val AwsS3Plugin: ApplicationPlugin<Unit> = createApplicationPlugin(
        name = "AwsS3Plugin") {


    }

interface AwsS3PluginInterface {
    var accessKey: String?
    var secretKey: String?
    var region: Regions
    public var bucketName: String
}

fun awsS3ClientConfig(accessKey: String?, secretKey: String?, region: Regions): AmazonS3 {
    val credentials: AWSCredentials = BasicAWSCredentials(accessKey, secretKey)
    if (accessKey != null && secretKey != null) {
        return AmazonS3ClientBuilder.standard()
            .withCredentials(AWSStaticCredentialsProvider(credentials))
            .withRegion(region)
            .build()
    } else {
        return AmazonS3ClientBuilder.standard()
            .withRegion(region)
            .build()
    }
}

fun Application.awsS3Plugin(config: AwsS3PluginInterface.() -> Unit) {
    val plugin = AwsS3PluginInterface::class.createInstance()
    plugin.config()
    val s3Client = awsS3ClientConfig(plugin.accessKey, plugin.secretKey, plugin.region)
    install(AwsS3Plugin) {
        s3Client
    }

    class bucketActions  {
        val s3Client = s3Client

        }
        suspend fun downloadFile(objectName: String, pathName: String) {
            val request = GetObjectRequest {
                bucket = plugin.bucketName
                key = objectName
            }
            S3Client { s3Client }.use { s3 ->
                s3.getObject(request) { resp ->
                    val myFile = File(pathName)
                    resp.body?.writeToFile(myFile)
                    println("Successfully read $objectName from ${plugin.bucketName} and saved to $pathName")
                }

            }
        }
        fun getS3ObjectUrl(objectName: String): String {
            return s3Client.getUrl(plugin.bucketName, objectName).toString()
        }

        fun listFiles(): List<String> {
            TODO()
        }

        fun uploadFile(file: String) {
            TODO()
        }

        fun getPresignUrl(objectName: String): String {
            val expiration = Date()
            val cal = Calendar.getInstance()
            cal.time = expiration
            cal.add(Calendar.MINUTE, 1)
            expiration.time = cal.timeInMillis
            return s3Client.generatePresignedUrl(plugin.bucketName, objectName, expiration).toString()
        }


    }
