package com.christianhturner.application

import com.christianhturner.plugins.*
import com.christianhturner.server.configureRouting

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.html.*
import io.ktor.server.http.content.*
import io.ktor.server.routing.*
import kotlinx.html.*

import io.github.cdimascio.dotenv.dotenv

val dotenv = dotenv()

fun HTML.index() {
    head {
        title("Hello from Ktor!")
    }
    body {
        div {
            +"Hello from Christian"
        }
        div {
            id = "root"
        }
        script(src = "/static/KMS-v2.js") {}
    }
}

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") //application.conf references the main function. This annotation prevents IDE from marking unused.
fun Application.module() {
    configureSecurity()
    configureHTTP()
    configureMonitoring()
    configureTemplating()
    configureSerialization()
    configureRouting()
    configureS3()
}

