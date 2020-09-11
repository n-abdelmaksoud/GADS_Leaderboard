package com.nermin.gadsleaderboard.ui.utils

import android.app.Activity
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import com.nermin.gadsleaderboard.R
import kotlinx.android.synthetic.main.dialog_confirmation.view.*
import kotlinx.android.synthetic.main.dialog_submission_result.view.*

fun Activity.createLoadingDialog(parentView: ViewGroup): AlertDialog =
    AlertDialog.Builder(this, R.style.AlertDialogRoundCorner)
        .setView(
            layoutInflater.inflate(
                R.layout.dialog_loading, parentView, false
            )
        )
        .setCancelable(false)
        .show()


fun Activity.createConfirmationDialog(parentView: ViewGroup, action: () -> Unit): AlertDialog {
    val view = layoutInflater.inflate(
        R.layout.dialog_confirmation,
        parentView,
        false
    ).apply {
        yesButton.setOnClickListener { action.invoke() }
    }
    return AlertDialog.Builder(this, R.style.AlertDialogRoundCorner)
        .setView(view)
        .setCancelable(false)
        .show()
        .apply {
            view.closeButton.setOnClickListener { dismiss() }
        }

}

fun Activity.createSubmissionResultDialog(
    parentView: ViewGroup,
    isSuccessful: Boolean,
    action: (Boolean) -> Unit
): AlertDialog {
    val view = layoutInflater.inflate(
        R.layout.dialog_submission_result,
        parentView,
        false
    ).apply {
        resultImageView.setImageResource(
            if (isSuccessful) R.drawable.ic_check
            else R.drawable.ic_warning
        )
        resultTextView.setText(
            if (isSuccessful) R.string.submission_success
            else R.string.submission_failed
        )
    }
    return AlertDialog.Builder(this, R.style.AlertDialogRoundCorner)
        .setCancelable(true)
        .setView(view)
        .setOnDismissListener { action.invoke(isSuccessful) }
        .show()
}