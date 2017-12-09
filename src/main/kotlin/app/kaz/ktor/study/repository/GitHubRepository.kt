package app.kaz.ktor.study.repository

import app.kaz.ktor.study.model.GitHubUser
import io.ktor.client.HttpClient
import io.ktor.client.call.call
import io.ktor.client.readText
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import org.json.simple.JSONObject
import org.json.simple.parser.JSONParser

interface GitHubRepository {
    suspend fun getUserInfo(): GitHubUser
}

class GitHubApiRepository(val client: HttpClient, val accessToken: String) : GitHubRepository {

    override suspend fun getUserInfo(): GitHubUser {
        val response = client.call("https://api.github.com/user?access_token=$accessToken") {
            header(HttpHeaders.Accept, listOf(ContentType.Application.FormUrlEncoded, ContentType.Application.Json).joinToString(","))
            header(HttpHeaders.Authorization, "token $accessToken")
            this.method = HttpMethod.Get
        }
        val body = response.readText()


        val parser = JSONParser()
        val jsonObj = parser.parse(body) as JSONObject
        return GitHubUser(
                jsonObj["id"] as Long,
                jsonObj["login"] as String,
                jsonObj["name"] as String,
                jsonObj["html_url"] as String,
                jsonObj["avatar_url"] as String)
    }

}