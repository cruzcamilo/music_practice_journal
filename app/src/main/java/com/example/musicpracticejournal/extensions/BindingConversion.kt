package com.example.musicpracticejournal.extensions

import android.view.View.GONE
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import androidx.databinding.BindingConversion

@BindingConversion
fun visibleOrGone(visible: Boolean?): Int {
    return if (visible == true) VISIBLE else GONE
}
@BindingConversion
fun visibleOrInvisible(visible: Boolean?): Int {
    return if (visible == true) VISIBLE else INVISIBLE
}