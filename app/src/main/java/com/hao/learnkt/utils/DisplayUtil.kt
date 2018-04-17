package com.hao.learnkt.utils

import android.content.Context

/**
 * @author Yang Shihao
 */

object DisplayUtil {
    fun getScreenWidth(context: Context): Int {
        return context.resources.displayMetrics.widthPixels
    }

    fun getScreenHeight(context: Context): Int {
        return context.resources.displayMetrics.heightPixels
    }
}