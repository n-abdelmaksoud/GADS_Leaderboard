package com.nermin.gadsleaderboard.repository

import com.nermin.gadsleaderboard.api.SubmissionService
import com.nermin.gadsleaderboard.api.submissionService
import com.nermin.gadsleaderboard.model.ErrorResponse
import com.nermin.gadsleaderboard.model.Response
import com.nermin.gadsleaderboard.model.SubmissionRequest
import com.nermin.gadsleaderboard.model.SuccessResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


val submissionRepository by lazy {
    SubmissionRepository(submissionService)
}

class SubmissionRepository(
    private val service: SubmissionService,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    suspend fun submit(request: SubmissionRequest): Response {
        return withContext(dispatcher) {
            performRequest {
                service.submit(
                    request.firstName,
                    request.lastName,
                    request.emailAddress,
                    request.projectLink
                )
            }
        }
    }

    private suspend fun performRequest(request: suspend () -> retrofit2.Response<Void>): Response {
        return try {
            val response = request.invoke()
            if (response.isSuccessful)
                SuccessResponse
            else
                ErrorResponse()
        } catch (throwable: Throwable) {
            throwable.handleError()
        }
    }
}