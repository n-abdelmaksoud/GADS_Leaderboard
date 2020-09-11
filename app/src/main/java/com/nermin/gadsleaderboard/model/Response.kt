package com.nermin.gadsleaderboard.model

sealed class Response

object Loading : Response()

object SuccessResponse : Response()

data class Success<T>(
    val list: List<T>
) : Response()

data class ErrorResponse(
    val message: String? = null,
    val code: Int? = null
) : Response()

