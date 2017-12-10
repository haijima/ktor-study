package app.kaz.ktor.study

import freemarker.cache.ClassTemplateLoader
import io.ktor.application.Application
import io.ktor.application.ApplicationStopping
import io.ktor.application.install
import io.ktor.client.HttpClient
import io.ktor.client.backend.apache.ApacheBackend
import io.ktor.features.CallLogging
import io.ktor.features.DefaultHeaders
import io.ktor.freemarker.FreeMarker
import io.ktor.locations.Locations
import io.ktor.locations.location
import io.ktor.routing.Routing
import io.ktor.sessions.SessionStorageMemory
import io.ktor.sessions.Sessions
import io.ktor.sessions.cookie

@location("/")
class Index()

@location("/login")
class Login()

@location("/login/github")
class LoginWithGitHub()

data class GitHubSession(val accessToken: String)

class App {

    fun Application.install() {

        val client = HttpClient(ApacheBackend)
        environment.monitor.subscribe(ApplicationStopping) {
            client.close()
        }
        install(DefaultHeaders)
        install(CallLogging)
        install(Locations)
        install(Sessions) {
            cookie<GitHubSession>("GitHubSession", SessionStorageMemory())
        }
        install(FreeMarker) {
            templateLoader = ClassTemplateLoader(App::class.java.classLoader, "templates")
        }
        install(Routing) {
            index(client)
            login()
            loginWithGitHub(client)
        }
    }
}
