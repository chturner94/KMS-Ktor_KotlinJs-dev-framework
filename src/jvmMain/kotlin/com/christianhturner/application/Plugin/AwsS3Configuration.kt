package com.christianhturner.application.Plugin

import com.amazonaws.auth.AWSCredentials
import com.amazonaws.auth.AWSStaticCredentialsProvider
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.regions.Regions
import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.AmazonS3ClientBuilder
import io.ktor.server.application.*

val AwsS3Plugin: ApplicationPlugin<AwsS3PluginConfiguration> = createApplicationPlugin(
        name = "AwsS3Plugin",
        createConfiguration = ::AwsS3PluginConfiguration,) {
    val accessKey = AwsS3PluginConfiguration.accessKey
    val secretKey = AwsS3PluginConfiguration.secretKey
    val region = AwsS3PluginConfiguration.region
    AwsS3PluginConfiguration.apply {
        fun awsS3ClientConfig(accessKey: String, secretKey: String, region: Regions) {
            val credentials: AWSCredentials = BasicAWSCredentials(accessKey, secretKey)
            var s3Client = AmazonS3ClientBuilder.standard()
                .withCredentials(AWSStaticCredentialsProvider(credentials))
                .withRegion(region)
                .build()
        }

    }
}
public class AwsS3PluginConfiguration {
    var accessKey: String = "Aws Access Key"
    var secretKey: String = "Aws Secret Key"
    var region: Regions = Regions.DEFAULT_REGION
}