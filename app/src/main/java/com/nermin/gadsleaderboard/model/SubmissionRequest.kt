package com.nermin.gadsleaderboard.model

data class SubmissionRequest(
    val firstName: String,
    val lastName: String,
    val emailAddress: String,
    val projectLink: String
)