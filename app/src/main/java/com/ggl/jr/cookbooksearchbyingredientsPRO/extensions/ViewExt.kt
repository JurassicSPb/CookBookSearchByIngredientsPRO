package com.ggl.jr.cookbooksearchbyingredientsPRO.extensions

import android.support.annotation.StringRes
import android.support.v7.widget.Toolbar
import android.text.TextUtils
import android.view.View
import android.widget.TextView

fun View.isClickableAndFocusable(state: Boolean) {
    isClickable = state
    isFocusable = state
}

fun View.visible(visible: Boolean) {
    visibility = if (visible) View.VISIBLE else View.GONE
}

fun Toolbar.setMarquee(@StringRes resId: Int, repeatLimit: Int = 1) {
    try {
        (javaClass.getDeclaredField("mTitleTextView").apply {
            isAccessible = true
        }.get(this) as? TextView)?.apply {
            ellipsize = TextUtils.TruncateAt.MARQUEE
            isFocusable = true
            isFocusableInTouchMode = true
            requestFocus()
            marqueeRepeatLimit = repeatLimit
            setSingleLine(true)
            isSelected = true
            setText(resId)
        }
    } catch (e: NoSuchFieldException) {
        e.printStackTrace()
    } catch (e: IllegalAccessException) {
        e.printStackTrace()
    }
}