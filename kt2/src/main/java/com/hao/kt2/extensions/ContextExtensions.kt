package com.hao.kt2.extensions

import android.content.Context
import android.support.v4.content.ContextCompat

/**
 * @author Yang Shihao
 */
fun Context.color(resId: Int): Int = ContextCompat.getColor(this, resId)