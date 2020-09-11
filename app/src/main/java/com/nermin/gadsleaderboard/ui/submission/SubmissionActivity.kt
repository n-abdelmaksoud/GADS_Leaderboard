package com.nermin.gadsleaderboard.ui.submission

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.nermin.gadsleaderboard.R
import com.nermin.gadsleaderboard.model.SubmissionRequest
import com.nermin.gadsleaderboard.ui.utils.*
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_submission.*
import kotlinx.android.synthetic.main.activity_submission.parentView
import kotlinx.android.synthetic.main.layout_app_bar.view.*

class SubmissionActivity : AppCompatActivity() {

    private var dialog: AlertDialog? = null
    private val viewModel: SubmissionViewModel by viewModels()
    private val disposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_submission)
        setupActionBar()
        setupSubmitButton()
        observeResults()
    }

    private fun setupActionBar() {
        appBarView.backButton.setOnClickListener {
            onBackPressed()
        }
    }

    private fun setupSubmitButton() {
        submitButton.setOnClickListener {
            hideSoftKeyboard()
            if (areInputFieldsComplete()) {
                displayConfirmationDialog()
            } else {
                handleInputError()
            }
        }
    }

    private fun observeResults() {
        observeSuccessResponse()
        observeErrorResponse()
        observeLoadingResponse()
    }

    private fun observeSuccessResponse() {
        disposable.add(viewModel.success.subscribe { isSuccess ->
            if (isSuccess) {
                inputViews.visibility = View.GONE
                dialog?.dismiss()
                dialog = createSubmissionResultDialog(parentView, true) {
                    finish()
                }
            }
        })
    }

    private fun observeErrorResponse() {
        disposable.add(viewModel.error.subscribe { isError ->
            if (isError) {
                inputViews.visibility = View.GONE
                dialog?.dismiss()
                dialog = createSubmissionResultDialog(parentView, false) {
                    dialog?.dismiss()
                    inputViews.visibility = View.VISIBLE
                }
            }
        })
    }

    private fun observeLoadingResponse() {
        disposable.add(viewModel.loading.subscribe { isLoading ->
            if (isLoading) displayLoadingDialog()
            else hideLoadingDialog()
        })
    }

    private fun handleInputError() {
        @StringRes val message = when {
            !firstNameEditText.isTextLengthValid() -> R.string.first_name_error
            !lastNameEditText.isTextLengthValid() -> R.string.last_name_error
            !emailEditText.isValidEmailAddress() -> R.string.email_error
            !githubLinkEditText.isValidUrl() -> R.string.link_error
            else -> null
        }
        message?.let { displayMessage(it) }
    }

    private fun displayConfirmationDialog() {
        dialog?.dismiss()
        dialog = createConfirmationDialog(parentView) {
            viewModel.submit(buildSubmitRequest())
        }
    }

    private fun buildSubmitRequest() = SubmissionRequest(
        firstNameEditText.editableText.toString(),
        lastNameEditText.editableText.toString(),
        emailEditText.editableText.toString(),
        githubLinkEditText.editableText.toString()
    )

    private fun areInputFieldsComplete() =
        firstNameEditText.isTextLengthValid() &&
                lastNameEditText.isTextLengthValid() &&
                emailEditText.isValidEmailAddress() &&
                githubLinkEditText.isValidUrl()

    private fun hideLoadingDialog() {
        dialog?.dismiss()
    }

    private fun displayLoadingDialog() {
        dialog?.dismiss()
        dialog = createLoadingDialog(parentView)
    }

    override fun onDestroy() {
        super.onDestroy()
        dialog?.dismiss()
        disposable.dispose()
        hideSoftKeyboard()
    }

    private fun hideSoftKeyboard() {
        (getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager).apply {
            hideSoftInputFromWindow(parentView.windowToken, 0)
        }
    }
}