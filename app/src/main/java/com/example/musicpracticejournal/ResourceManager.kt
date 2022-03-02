package com.example.musicpracticejournal

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat

class ResourceManager(private val context: Context) {

    fun getString(@StringRes id: Int): String {
        return context.getString(id)
    }

    fun getString(@StringRes id: Int, vararg arguments: String?): String {
        return context.getString(id, *arguments)
    }

    fun getColor(@ColorRes id: Int): Int {
        return ContextCompat.getColor(context, id)
    }

    fun getDrawable(@DrawableRes id: Int): Drawable {
        return ContextCompat.getDrawable(context, id)!!
    }
}