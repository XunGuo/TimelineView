package com.alorma.timeline

import android.content.Context
import android.os.Build
import android.util.TypedValue

object AttributesUtils {

    fun colorPrimary(context: Context, defaultValue: Int): Int {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getColor(context, android.R.attr.colorPrimary, defaultValue)
        } else getColor(context, R.attr.colorPrimary, defaultValue)
    }

    private fun getColor(context: Context, id: Int, defaultValue: Int): Int {
        val value = TypedValue()

        val theme = context.theme
        if (theme != null && theme.resolveAttribute(id, value, true)) {
            if (value.type >= TypedValue.TYPE_FIRST_INT && value.type <= TypedValue.TYPE_LAST_INT) {
                return value.data
            } else if (value.type == TypedValue.TYPE_STRING) {
                return context.resources.getColor(value.resourceId)
            }
        }
        return defaultValue
    }
}