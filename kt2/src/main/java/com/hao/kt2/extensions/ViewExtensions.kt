package com.hao.kt2.extensions

import android.content.Context
import android.view.View
import android.widget.TextView

/**
 * @author Yang Shihao
 */
val View.ctx: Context
    get() = context

var TextView.textColor: Int
    get() = currentTextColor
    set(value) = setTextColor(value)

fun View.slideExit() {
    if (translationY == 0F) animate().translationY(-height.toFloat())
}

fun View.slideEnter() {
    if (translationY < 0F) animate().translationY(0F)
}