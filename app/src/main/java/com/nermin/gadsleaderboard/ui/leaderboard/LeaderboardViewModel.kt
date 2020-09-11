package com.nermin.gadsleaderboard.ui.leaderboard

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nermin.gadsleaderboard.model.*
import com.nermin.gadsleaderboard.repository.leaderBoardRepository
import kotlinx.coroutines.launch

class LeaderboardViewModel : ViewModel() {
    val learningLeaders = MutableLiveData<Response>()
    val skillIQLeaders = MutableLiveData<Response>()

    init {
        loadLeaders()
    }

    private fun loadLeaders() {
        learningLeaders.value = Loading
        skillIQLeaders.value = Loading
        viewModelScope.launch {
            learningLeaders.postValue(leaderBoardRepository.getLearningLeaders())
            skillIQLeaders.postValue(leaderBoardRepository.getSkillIqLeaders())
        }
    }

}