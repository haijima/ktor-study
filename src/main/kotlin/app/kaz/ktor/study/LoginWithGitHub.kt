package app.kaz.ktor.study

import io.ktor.application.application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.auth.Authentication
import io.ktor.auth.OAuthAccessTokenResponse
import io.ktor.auth.OAuthServerSettings
import io.ktor.auth.principal
import io.ktor.client.HttpClient
import io.ktor.locations.location
import io.ktor.locations.locations
import io.ktor.locations.oauthAtLocation
import io.ktor.response.respondRedirect
import io.ktor.response.respondText
import io.ktor.routing.Route
import io.ktor.sessions.sessions
import io.ktor.sessions.set
import kotlinx.coroutines.experimental.asCoroutineDispatcher
import java.util.concurrent.Executors

private val exec = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 4)

private val gitHubOAuth2Settings = OAuthServerSettings.OAuth2ServerSettings(
        name = "github",
        authorizeUrl = "https://github.com/login/oauth/authorize",
        accessTokenUrl = "https://github.com/login/oauth/access_token",
        clientId = "b59e787c3af0cb9f503d",
        clientSecret = "d8f48d1f9d9edabd3d9e1a1ca901ac027083a84a",
        defaultScopes = listOf("read:user")
)

fun Route.loginWithGitHub(client: HttpClient) {
    location<LoginWithGitHub> {
        install(Authentication) {
            oauthAtLocation<LoginWithGitHub>(
                    client,
                    exec.asCoroutineDispatcher(),
                    providerLookup = { gitHubOAuth2Settings },
                    urlProvider = { location, _ -> "http://localhost:8080${application.locations.href(location)}" })
        }

        handle {
            val principal = call.principal<OAuthAccessTokenResponse.OAuth2>()
            if (principal != null) {
                call.sessions.set(GitHubSession(principal.accessToken))
                call.respondRedirect(application.locations.href(Index()))
            } else {
                call.respondText { "Login Error" }
            }

        }
    }
}
