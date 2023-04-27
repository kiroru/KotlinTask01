package jp.kiroru.kotlintask01

import retrofit2.Call
import retrofit2.http.GET

interface WebApi {
    @GET("/users")
    fun getGitHubUsers(): Call<List<GitHubUserEntity>>
}