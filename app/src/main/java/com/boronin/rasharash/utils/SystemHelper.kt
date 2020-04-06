package com.boronin.rasharash.utils

import android.content.Context
import android.view.inputmethod.InputMethodManager
import android.widget.EditText


object SystemHelper {

    fun hideKeyboard(context: Context, editText: EditText) {
        editText.clearFocus()
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
        imm!!.hideSoftInputFromWindow(editText.windowToken, 0)
    }
}