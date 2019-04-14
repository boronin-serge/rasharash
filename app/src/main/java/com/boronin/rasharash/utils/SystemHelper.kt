package com.boronin.rasharash.utils

import android.content.Context
import android.view.inputmethod.InputMethodManager
import android.widget.EditText


class SystemHelper private constructor() {

    fun hideKeyboard(context: Context, editText: EditText) {
        editText.clearFocus()
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
        imm!!.hideSoftInputFromWindow(editText.windowToken, 0)
    }

    companion object {
        val INSTANCE = SystemHelper()
    }
}