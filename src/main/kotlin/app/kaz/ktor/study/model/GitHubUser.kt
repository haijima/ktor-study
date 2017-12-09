package app.kaz.ktor.study.model

data class GitHubUser(val id: Long,
                      val login: String,
                      val name: String,
                      val url: String,
                      val avatarUrl: String)