package com.nermin.gadsleaderboard.ui.submission

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nermin.gadsleaderboard.model.*
import com.nermin.gadsleaderboard.repository.leaderBoardRepository
import com.nermin.gadsleaderboard.repository.submissionRepository
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SubmissionViewModel : ViewModel() {

    val error = PublishSubject.create<Boolean>()
    val success = BehaviorSubject.create<Boolean>()
    val loading = BehaviorSubject.create<Boolean>()

    fun submit(submitRequest: SubmissionRequest) {
        loading.onNext(true)
        viewModelScope.launch {
            handleResponse(submissionRepository.submit(submitRequest))
        }
    }

    private suspend fun handleResponse(response: Response) {
        withContext(Dispatchers.Main) {
            loading.onNext(false)
            when (response) {
                is SuccessResponse -> success.onNext(true)
                is ErrorResponse -> error.onNext(true)
            }
        }
    }

}