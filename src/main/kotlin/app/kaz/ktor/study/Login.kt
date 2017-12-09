package app.kaz.ktor.study

import io.ktor.application.application
import io.ktor.application.call
import io.ktor.freemarker.FreeMarkerContent
import io.ktor.locations.get
import io.ktor.locations.locations
import io.ktor.response.respond
import io.ktor.response.respondRedirect
import io.ktor.routing.Route
import io.ktor.sessions.get
import io.ktor.sessions.sessions

fun Route.login() {
    get<Login> {
        val session = call.sessions.get<GitHubSession>()
        if (session != null) {
            call.respondRedirect(application.locations.href(Index()))
        } else {
            call.respond(FreeMarkerContent("login.ftl", emptyMap<String, Any>(), ""))
        }
    }
}
