package com.nermin.gadsleaderboard.api

import com.nermin.gadsleaderboard.model.LearningLeader
import com.nermin.gadsleaderboard.model.SkillIQLeader
import retrofit2.http.GET


val leaderBoardService by lazy {
    leaderBoardRetrofit.create(LeaderBoardService::class.java)
}

interface LeaderBoardService {

    @GET("/api/skilliq")
    suspend fun getSkillIQLeaders(): List<SkillIQLeader>


    @GET("/api/hours")
    suspend fun getLearningLeaders(): List<LearningLeader>
}
