package com.example.githubrepousers.app.network

import com.example.githubrepousers.app.models.RepoResponse
import com.example.githubrepousers.app.models.UsersResponse
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

class Requester() {

    interface RequestService {

        @Headers("Accept: application/vnd.github+json")
        @GET("/search/repositories")
        suspend fun searchRepos(
            @Query("q") query: String,
            @Query("page") page: Int,
            @Query("per_page") per_page: Int,
        ): Response<RepoResponse>

        @Headers("Accept: application/vnd.github+json")
        @GET("/search/users")
        suspend fun searchUsers(
            @Query("q") query: String,
            @Query("page") page: Int,
            @Query("per_page") per_page: Int,
        ): Response<UsersResponse>

        @Headers(
            "Accept: application/vnd.github+json",
            "Authorization: Bearer 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",
            "X-GitHub-Api-Version: 2022-11-28"
        )
        @GET("/users/{login}")
        suspend fun getUser(
            @Path("login") login: String
        ): Response<UsersResponse>
    }

    companion object {
        private var basePath = "https://api.github.com"

        //Create interceptor
        private val interceptor = Interceptor { chain ->
            val request = chain.request()
            val requestBuilder = request.newBuilder()
            val url = request.url
            val builder = url.newBuilder()
            requestBuilder.url(builder.build())
                .method(request.method, request.body)
                .addHeader("clientType", "IOS")
                .addHeader("Content-Type", "application/json")
            chain.proceed(requestBuilder.build())
        }

        //Create okhttp
        private val client: OkHttpClient.Builder = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .retryOnConnectionFailure(false)


        private var retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(basePath)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client.build())
            .build()

        var service: RequestService = retrofit.create(RequestService::class.java)
    }
}

sealed class UIState<out T>() {
    class Idle() : UIState<Nothing>()
    class Loading(val progress: Int = 0) : UIState<Nothing>()
    class Success<out T>(val data: T?) : UIState<T>()
    class Error(
        val error: Throwable? = null,
        val message: String? = null,
        val title: String? = null
    ) : UIState<Nothing>()
}

data class ResponseData<out T>(
    val msg: String,
    val code: Int,
    val data: T?,
)