package com.study.dongamboard.api

import com.study.dongamboard.model.PostData
import com.study.dongamboard.model.request.PostRequest
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @GET("/api/post")
    suspend fun getAllPost(): List<PostData>

    @GET("/api/post/{postId}")
    suspend fun getPostById(@Path(value = "postId") id: Int): PostData

    @POST("/api/post")
    suspend fun createPost(@Body postRequest: PostRequest)

    @PATCH("/api/post/{postId}")
    suspend fun updatePost(@Path(value = "postId") id: Int, @Body postRequest: PostRequest)

    @DELETE("/api/post/{postId}")
    suspend fun deletePost(@Path(value = "postId") id: Int)

    @POST("/api/post/like/{postId}")
    suspend fun clickPostLike(@Path(value = "postId") id: Int)
}