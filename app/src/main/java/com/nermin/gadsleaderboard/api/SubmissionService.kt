package com.nermin.gadsleaderboard.api

import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST


val submissionService by lazy {
    submissionRetrofit.create(SubmissionService::class.java)
}

interface SubmissionService {
    @POST("1FAIpQLSf9d1TcNU6zc6KR8bSEM41Z1g1zl35cwZr2xyjIhaMAz8WChQ/formResponse")
    @FormUrlEncoded
    suspend fun submit(
        @Field("entry.1824927963") email: String,
        @Field("entry.1877115667") firstName: String,
        @Field("entry.2006916086") lastName: String,
        @Field("entry.284483984") projectLink: String,
    ): Response<Void>
}