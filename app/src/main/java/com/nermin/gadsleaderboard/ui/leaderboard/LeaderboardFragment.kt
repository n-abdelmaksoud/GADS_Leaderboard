package com.nermin.gadsleaderboard.ui.leaderboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.nermin.gadsleaderboard.R
import com.nermin.gadsleaderboard.model.ErrorResponse
import com.nermin.gadsleaderboard.model.Leader
import com.nermin.gadsleaderboard.model.Loading
import com.nermin.gadsleaderboard.model.Response
import com.nermin.gadsleaderboard.model.Success
import com.nermin.gadsleaderboard.ui.leaderboard.adapter.LeaderboardAdapter
import com.nermin.gadsleaderboard.ui.utils.createLoadingDialog
import com.nermin.gadsleaderboard.ui.utils.displayMessage
import kotlinx.android.synthetic.main.fragment_leaderboard.*

private const val BUNDLE_TYPE_IS_LEARNING_LEADER = "data_type"

class LeaderboardFragment : Fragment() {

    private val viewModel: LeaderboardViewModel by activityViewModels()
    private var isLearningLeaderType: Boolean = false
    private val adapter = LeaderboardAdapter()
    private var dialog: AlertDialog? = null

    companion object {
        fun start(isLearningLeaderType: Boolean): Fragment {
            return LeaderboardFragment().apply {
                arguments = Bundle().apply {
                    putBoolean(BUNDLE_TYPE_IS_LEARNING_LEADER, isLearningLeaderType)
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_leaderboard, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isLearningLeaderType = arguments?.getBoolean(BUNDLE_TYPE_IS_LEARNING_LEADER) ?: false
        setupView()
        observe()
    }

    private fun setupView() {
        recyclerView.adapter = adapter
    }

    private fun observe() {
        if (isLearningLeaderType)
            observeLearningLeader()
        else
            observeSkillIQLeader()
    }

    private fun observeLearningLeader() {
        viewModel.learningLeaders.observe(viewLifecycleOwner, {
            handleResponse(it)
        })
    }

    private fun observeSkillIQLeader() {
        viewModel.skillIQLeaders.observe(viewLifecycleOwner, {
            handleResponse(it)
        })
    }

    private fun handleResponse(response: Response) {
        when (response) {
            Loading -> displayLoadingDialog()
            is Success<*> -> displayResultList(response.list as List<Leader>)
            is ErrorResponse -> handleError(response)
        }
    }

    private fun displayResultList(list: List<Leader>) {
        hideLoadingDialog()
        adapter.updateList(list)
    }

    private fun handleError(errorResponse: ErrorResponse) {
        hideLoadingDialog()
        val message = errorResponse.message ?: getString(R.string.general_error)
        requireActivity().displayMessage(message)
    }

    private fun hideLoadingDialog() {
        dialog?.dismiss()
    }

    private fun displayLoadingDialog() {
        dialog?.dismiss()
        dialog = activity?.createLoadingDialog(parentView)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        hideLoadingDialog()
    }

}