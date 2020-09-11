package com.nermin.gadsleaderboard.repository

import com.nermin.gadsleaderboard.api.LeaderBoardService
import com.nermin.gadsleaderboard.api.leaderBoardService
import com.nermin.gadsleaderboard.model.ErrorResponse
import com.nermin.gadsleaderboard.model.Response
import com.nermin.gadsleaderboard.model.Success
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException

val leaderBoardRepository by lazy {
    LeaderBoardRepository(leaderBoardService)
}

class LeaderBoardRepository(
    private val leaderBoardService: LeaderBoardService,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    suspend fun getSkillIqLeaders(): Response {
        return withContext(dispatcher) {
            performRequest { leaderBoardService.getSkillIQLeaders() }
        }
    }

    suspend fun getLearningLeaders(): Response {
        return withContext(dispatcher) {
            performRequest { leaderBoardService.getLearningLeaders() }
        }
    }

    private suspend fun <T> performRequest(request: suspend () -> List<T>): Response {
        return try {
            Success(request.invoke())
        } catch (throwable: Throwable) {
            throwable.handleError()
        }
    }
}

fun Throwable.handleError(): ErrorResponse {
    return when (this) {
        is HttpException -> ErrorResponse(
            this.message,
            this.code()
        )
        else -> ErrorResponse()
    }
}