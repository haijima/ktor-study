//package io.ktor.samples.testable

import io.ktor.application.Application
import io.ktor.application.ApplicationCallPipeline
import io.ktor.application.call
import io.ktor.request.uri
import io.ktor.response.respondText


fun Application.testableApplication() {
    intercept(ApplicationCallPipeline.Call) {
        if (call.request.uri == "/")
            call.respondText("Test String")
    }
}
