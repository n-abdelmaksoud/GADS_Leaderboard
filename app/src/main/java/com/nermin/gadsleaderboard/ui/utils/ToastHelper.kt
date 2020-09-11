package com.nermin.gadsleaderboard.ui.utils

import android.app.Activity
import android.widget.Toast
import androidx.annotation.StringRes

fun Activity.displayMessage(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

fun Activity.displayMessage(@StringRes message: Int) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}