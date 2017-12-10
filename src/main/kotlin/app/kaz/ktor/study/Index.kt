package app.kaz.ktor.study

import app.kaz.ktor.study.repository.GitHubApiRepository
import io.ktor.application.application
import io.ktor.application.call
import io.ktor.client.HttpClient
import io.ktor.freemarker.FreeMarkerContent
import io.ktor.locations.get
import io.ktor.locations.locations
import io.ktor.response.respond
import io.ktor.response.respondRedirect
import io.ktor.routing.Route
import io.ktor.sessions.get
import io.ktor.sessions.sessions

fun Route.index(client: HttpClient) {
    get<Index> {
        val user = call.sessions.get<GitHubSession>()?.let { GitHubApiRepository(client, it.accessToken).getUserInfo() }
        if (user != null) {
            val etag = user.id.hashCode()
            call.respond(FreeMarkerContent("index.ftl", mapOf("user" to user), etag.toString()))
        } else {
            call.respondRedirect(application.locations.href(Login()))
        }
    }
}
