package com.study.dongamboard.api

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.skydoves.sandwich.adapters.ApiResponseCallAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object APIObject {
    private const val BASE_URL ="http://10.0.2.2:8080/"

    val moshi = Moshi.Builder()
        .addLast(KotlinJsonAdapterFactory())
        .build()

    private val getRetrofit by lazy {
        Retrofit.Builder()
            .client(
                OkHttpClient.Builder()
                    .authenticator(TokenAuthenticator())
                    .build()
            )
            .baseUrl(BASE_URL)
            .addCallAdapterFactory(ApiResponseCallAdapterFactory.create())
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

    val getRetrofitAPIService: APIService by lazy { getRetrofit.create(APIService::class.java) }
}
