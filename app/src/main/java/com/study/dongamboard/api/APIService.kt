package com.study.dongamboard.api

import com.skydoves.sandwich.ApiResponse
import com.study.dongamboard.model.request.CheckPasswordForMyPageRequest
import com.study.dongamboard.model.request.CommentRequest
import com.study.dongamboard.model.request.NoticeRequest
import com.study.dongamboard.model.response.PostResponse
import com.study.dongamboard.model.request.PostRequest
import com.study.dongamboard.model.request.SignInRequest
import com.study.dongamboard.model.request.UpdateNicknameRequest
import com.study.dongamboard.model.request.UpdatePasswordRequest
import com.study.dongamboard.model.request.UserRequest
import com.study.dongamboard.model.response.AllPostByCategoryResponse
import com.study.dongamboard.model.response.BaseResponse
import com.study.dongamboard.model.response.CommentResponse
import com.study.dongamboard.model.response.MyInformationResponse
import com.study.dongamboard.model.response.NoticeResponse
import com.study.dongamboard.model.response.SignInResponse
import com.study.dongamboard.type.BoardCategory
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
        @Query(value = "category") category: BoardCategory): ApiResponse<BaseResponse<AllPostByCategoryResponse>>

    @GET("/api/post/{postId}")
    suspend fun getPostById(@Path(value = "postId") id: Long): ApiResponse<BaseResponse<PostResponse>>

    @POST("/api/post")
    suspend fun createPost(@Body postRequest: PostRequest): ApiResponse<Unit>

    @PATCH("/api/post/{postId}")
    suspend fun updatePost(@Path(value = "postId") id: Long, @Body postRequest: PostRequest): ApiResponse<Unit>

    @DELETE("/api/post/{postId}")
    suspend fun deletePost(@Path(value = "postId") id: Long): ApiResponse<Unit>

    @POST("/api/post/like/{postId}")
    suspend fun clickPostLike(@Path(value = "postId") id: Long): ApiResponse<Unit>

    @GET("/api/post/{postId}/comment")
    suspend fun getAllComment(
        @Path(value = "postId") postId: Long,
        @Query(value = "size") size: Int,
        @Query(value = "page") page: Int,
        @Query(value = "lastCommentId") lastCommentId: Long): ApiResponse<List<CommentResponse>>

    @POST("/api/post/{postId}/comment")
    suspend fun createComment(
        @Path(value = "postId") postId: Long,
        @Body commentRequest: CommentRequest): ApiResponse<Unit>

    @GET("/api/notice")
    suspend fun getAllNotice(
        @Query(value = "size") size: Int,
        @Query(value = "page") page: Int): ApiResponse<BaseResponse<List<NoticeResponse>>>

    @GET("/api/notice/{noticeId}")
    suspend fun getNoticeById(@Path(value = "noticeId") id: Long): ApiResponse<NoticeResponse>

    @POST("/api/notice")
    suspend fun createNotice(@Body noticeRequest: NoticeRequest): ApiResponse<Unit>

    @PATCH("/api/notice/{noticeId}")
    suspend fun updateNotice(@Path(value = "noticeId") id: Long, @Body noticeRequest: NoticeRequest): ApiResponse<Unit>

    @DELETE("/api/notice/{noticeId}")
    suspend fun deleteNotice(@Path(value = "noticeId") id: Long): ApiResponse<Unit>

    @GET("/api/user")
    suspend fun getMyInformation(): ApiResponse<BaseResponse<MyInformationResponse>>

    @POST("/api/user/sign-up")
    suspend fun createUser(@Body userRequest: UserRequest): ApiResponse<Unit>

    @POST("/api/user/sign-in")
    suspend fun signIn(@Body signInRequest: SignInRequest): ApiResponse<BaseResponse<SignInResponse>>

    @POST("/api/user/my-page/password")
    suspend fun checkPasswordForMyPage(@Body checkPasswordForMyPageRequest: CheckPasswordForMyPageRequest): ApiResponse<Unit>

    @PATCH("/api/user/password")
    suspend fun updatePassword(@Body passwordRequest: UpdatePasswordRequest): ApiResponse<Unit>

    @PATCH("/api/user/nickname")
    suspend fun updateNickname(@Body nicknameRequest: UpdateNicknameRequest): ApiResponse<Unit>

    @DELETE("api/user")
    suspend fun deleteUser(): ApiResponse<Unit>
}