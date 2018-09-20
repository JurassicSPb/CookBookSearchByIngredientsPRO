package com.ggl.jr.cookbooksearchbyingredientsPRO.extensions

import android.view.View

fun View.isClickableAndFocusable(state: Boolean) {
    isClickable = state
    isFocusable = state
}

fun View.visible(visible: Boolean) {
    visibility = if (visible) View.VISIBLE else View.GONE
}