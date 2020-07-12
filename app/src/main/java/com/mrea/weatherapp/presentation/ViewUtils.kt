package com.mrea.weatherapp.presentation

import android.content.res.Resources
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.findFragment
import androidx.lifecycle.LifecycleOwner

/**
 * Converts DP to Pixels
 **/
val Int.px: Int get() = (this * Resources.getSystem().displayMetrics.density).toInt()

/**
 * Returns this View's containing Fragment's viewLifecycleOwner or the containing AppCompatActivity
 */
fun View.findLifecycleOwner(): LifecycleOwner = runCatching {
    this.findFragment<Fragment>().viewLifecycleOwner
}.getOrDefault(this.context as AppCompatActivity)

fun View.showKeyboard() {
    if (hasFocus().not()) {
        ContextCompat.getSystemService(context, InputMethodManager::class.java)?.run {
            toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
        }
        requestFocus()
    }
}

fun View.hideKeyboard() {
    ContextCompat.getSystemService(context, InputMethodManager::class.java)?.run {
        hideSoftInputFromWindow(windowToken, 0)
    }
}