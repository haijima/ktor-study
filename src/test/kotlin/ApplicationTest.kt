//package io.ktor.samples.testable.tests

import io.ktor.application.Application
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.server.testing.handleRequest
import io.ktor.server.testing.withTestApplication
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse

class ApplicationTest {

    @Test
    fun testRequest() = withTestApplication(Application::testableApplication) {
        with(handleRequest(HttpMethod.Get, "/")) {
            assertEquals(HttpStatusCode.OK, response.status())
            assertEquals("Test String", response.content)
        }
        with(handleRequest(HttpMethod.Get, "/index.html")) {
            assertFalse(requestHandled)
        }
    }

}