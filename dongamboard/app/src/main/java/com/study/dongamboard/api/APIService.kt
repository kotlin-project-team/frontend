package com.study.dongamboard.api

import com.skydoves.sandwich.ApiResponse
import com.study.dongamboard.model.request.NoticeRequest
import com.study.dongamboard.model.response.PostResponse
import com.study.dongamboard.model.request.PostRequest
import com.study.dongamboard.model.request.UserRequest
import com.study.dongamboard.model.response.NoticeResponse
import com.study.dongamboard.type.BoardCategoryType
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface APIService {
    @GET("/api/post")
    suspend fun getAllPost(
        @Query(value = "size") size: Int,
        @Query(value = "page") page: Int,
        @Query(value = "category") category: BoardCategoryType): ApiResponse<List<PostResponse>>

    @GET("/api/post/{postId}")
    suspend fun getPostById(@Path(value = "postId") id: Int): ApiResponse<PostResponse>

    @POST("/api/post")
    suspend fun createPost(@Body postRequest: PostRequest)

    @PATCH("/api/post/{postId}")
    suspend fun updatePost(@Path(value = "postId") id: Int, @Body postRequest: PostRequest)

    @DELETE("/api/post/{postId}")
    suspend fun deletePost(@Path(value = "postId") id: Int)

    @POST("/api/post/like/{postId}")
    suspend fun clickPostLike(@Path(value = "postId") id: Int)

    @GET("/api/notice")
    suspend fun getAllNotice(
        @Query(value = "size") size: Int,
        @Query(value = "page") page: Int): ApiResponse<List<NoticeResponse>>

    @POST("/api/notice")
    suspend fun createNotice(@Body noticeRequest: NoticeRequest): ApiResponse<Unit>

    @PATCH("/api/notice/{noticeId}")
    suspend fun updateNotice(@Path(value = "noticeId") id: Int, @Body noticeRequest: NoticeRequest): ApiResponse<Unit>

    @DELETE("/api/notice/{noticeId}")
    suspend fun deleteNotice(@Path(value = "noticeId") id: Int): ApiResponse<Unit>

    @POST("/api/user")
    suspend fun createUser(@Body userRequest: UserRequest): ApiResponse<Unit>
}