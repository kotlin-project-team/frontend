package com.study.dongamboard.api

import com.study.dongamboard.model.PostData
import retrofit2.http.GET

interface APIService {

    @GET("/posts")  // test를 위한 url, 추후 board로 변경
    suspend fun getAllPosts(): List<PostData>

}