
plugins {
    kotlin("multiplatform") version "1.7.10"
    application
    kotlin("plugin.serialization") version "1.4.0"
}

group = "com.christianhturner"
version = "0.0.1"

repositories {
    jcenter()
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/kotlinx-html/maven")
}

kotlin {
    jvm {
        compilations.all {
            kotlinOptions.jvmTarget = "1.8"
        }
        withJava()
        testRuns["test"].executionTask.configure {
            useJUnitPlatform()
        }
    }
    js(IR) {
        binaries.executable()
        browser {
            commonWebpackConfig {
                cssSupport.enabled = true

            }
        }
    }
    sourceSets {
        val commonMain by getting {
            kotlin.srcDir("src/commonMain/kotlin")
            resources.srcDir("src/commonMain/resources")
        }
        val commonTest by getting {
            kotlin.srcDir("src/commonTest/kotlin")
            resources.srcDir("src/commonTest/resources")
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val jvmMain by getting {
            kotlin.srcDir("src/jvmMain/kotlin")
            resources.srcDir("src/jvmMain/resources")
            dependencies {
                implementation("io.ktor:ktor-server-core-jvm:2.0.3")
                implementation("io.ktor:ktor-server-auth-jvm:2.0.3")
                implementation("io.ktor:ktor-server-host-common:2.0.3")
                implementation("io.ktor:ktor-server-status-pages-jvm:2.0.3")
                implementation("io.ktor:ktor-server-content-negotiation-jvm:2.0.3")
                implementation("io.ktor:ktor-server-netty:2.0.3")
                implementation("io.ktor:ktor-server-html-builder-jvm:2.0.3")
                implementation("org.jetbrains.kotlinx:kotlinx-html-jvm:0.7.2")
                implementation("org.jetbrains:kotlin-css-jvm:1.0.0-pre.129-kotlin-1.4.20")
                implementation("io.ktor:ktor-server-cors-jvm:2.0.3")
                implementation("io.ktor:ktor-server-metrics-jvm:2.0.3")
                implementation("io.ktor:ktor-server-metrics-micrometer-jvm:2.0.3")
                implementation("io.micrometer:micrometer-registry-prometheus:1.9.3")
                implementation("io.ktor:ktor-server-call-logging-jvm:2.0.3")
                implementation("io.ktor:ktor-server-call-id-jvm:2.0.3")
                implementation("io.ktor:ktor-server-html-builder-jvm:2.0.3")
                implementation("io.ktor:ktor-serialization-kotlinx-json-jvm:2.0.3")
                implementation("ch.qos.logback:logback-classic:1.2.11")

                implementation("aws.sdk.kotlin:s3:0.9.4-beta")
                implementation("aws.sdk.kotlin:iam:0.9.4-beta")
                implementation("com.amazonaws:aws-java-sdk-s3:1.11.1000")

                implementation("io.github.cdimascio:dotenv-kotlin:6.3.1")
            }
        }
        val jvmTest by getting {
            kotlin.srcDir("src/jvmTest/kotlin")
            resources.srcDir("src/jvmTest/resources")
            dependencies {
                implementation(kotlin("test-junit"))
            }
        }
        val jsMain by getting {
            kotlin.srcDir("src/jsMain/kotlin")
            resources.srcDir("src/jsMain/resources")
            dependencies {
                implementation(kotlin("stdlib-js"))
                implementation("org.jetbrains.kotlin-wrappers:kotlin-react:18.0.0-pre.332-kotlin-1.6.21")
                implementation("org.jetbrains.kotlin-wrappers:kotlin-react-dom:18.0.0-pre.332-kotlin-1.6.21")
                implementation("org.jetbrains.kotlin-wrappers:kotlin-emotion:11.9.0-pre.332-kotlin-1.6.21")
            }
        }
        val jsTest by getting
    }
}

application {
    mainClass.set("io.ktor.server.netty.EngineMain")
    //remove for production
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=true")
}

tasks.named<Copy>("jvmProcessResources") {
    val jsBrowserDistribution = tasks.named("jsBrowserDistribution")
    from(jsBrowserDistribution)
}

tasks.named<JavaExec>("run") {
    dependsOn(tasks.named<Jar>("jvmJar"))
    classpath(tasks.named<Jar>("jvmJar"))
}
