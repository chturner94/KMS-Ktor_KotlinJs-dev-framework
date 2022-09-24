package com.christianhturner.application

import aws.sdk.kotlin.runtime.auth.credentials.*
import com.christianhturner.application.Plugin.*
import io.ktor.server.application.*

import aws.sdk.kotlin.services.s3.*
import com.amazonaws.*
import com.amazonaws.regions.Regions
import com.amazonaws.services.s3.model.SSECustomerKey

import io.github.cdimascio.dotenv.dotenv

fun Application.configureS3() {
    install(AwsS3Plugin)
    val dotenv = dotenv()

    class AwsS3PluginConfiguration : AwsS3PluginInterface {
        override var accessKey: String? = dotenv["AWS_ACCESS"]
        override var secretKey: String? = dotenv["AWS_SECRET"]
        override var region: Regions = Regions.US_EAST_1
        override var bucketName: String = "kms-test-with-iam"



    }
//Create routes here

    }