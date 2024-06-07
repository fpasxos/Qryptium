package com.fps.core.presentation.ui

import android.content.Context
import androidx.annotation.StringRes

// UiText is a 'wrapper' around either a real string or a string resource. In the VM, we will
// have the option to wrap some string and delegate it to the UI, which will unwrap this string
sealed interface UiText {
    data class DynamicString(val value: String) :
        UiText // just a dynamic string, which we do not want to localize

    class StringResource( // here
        @StringRes val id: Int,
        val args: Array<Any> = arrayOf()
    ) : UiText

    fun asString(context: Context): String {
        return when (this) {
            is DynamicString -> value
            is StringResource -> context.getString(id, *args)
        }
    }
}