package com.nermin.gadsleaderboard.model

sealed class Leader

data class SkillIQLeader(
    val name: String,
    val score: Int,
    val country: String,
    val badgeUrl: String
) : Leader()

data class LearningLeader(
    val name: String,
    val hours: Int,
    val country: String,
    val badgeUrl: String
) : Leader()