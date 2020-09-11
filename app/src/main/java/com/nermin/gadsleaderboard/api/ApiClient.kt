package com.nermin.gadsleaderboard.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL_SUBMISSION = "https://docs.google.com/forms/d/e/"
const val BASE_URL_LEADER_BOARD = "https://gadsapi.herokuapp.com"

private val interceptor = HttpLoggingInterceptor()

private val client by lazy {
    interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
    OkHttpClient
        .Builder()
        .addInterceptor(interceptor)
        .build()
}

val leaderBoardRetrofit by lazy {
    Retrofit.Builder()
        .baseUrl(BASE_URL_LEADER_BOARD)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}

val submissionRetrofit by lazy {
    Retrofit.Builder()
        .baseUrl(BASE_URL_SUBMISSION)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}
