package com.nermin.gadsleaderboard.ui.utils

import android.util.Patterns
import android.webkit.URLUtil
import android.widget.EditText

private const val TEXT_LENGTH_MIN = 2

fun EditText.isTextLengthValid(minLength: Int = TEXT_LENGTH_MIN) =
    isNotEmpty() &&
            editableText.toString().length >= minLength

fun EditText.isValidEmailAddress() =
    isNotEmpty() &&
            Patterns.EMAIL_ADDRESS.matcher(editableText.toString()).matches()

fun EditText.isValidUrl() =
    isNotEmpty() &&
            URLUtil.isValidUrl(editableText.toString())


private fun EditText.isNotEmpty() =
    editableText?.toString()?.isEmpty() == false

