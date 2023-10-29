package com.study.dongamboard.api

import com.study.dongamboard.model.PostData
import com.study.dongamboard.model.request.PostRequest
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface APIService {
    @GET("/api/post")
    suspend fun getAllPost(): List<PostData>

    @GET("/api/post/{postId}")
    suspend fun getPostById(@Path(value = "postId") id: Int): PostData

    @POST("/api/post")
    suspend fun createPost(@Body postRequest: PostRequest)

    @POST("/api/post/like/{postId}")
    suspend fun clickPostLike(@Path(value = "postId") id: Int)
}