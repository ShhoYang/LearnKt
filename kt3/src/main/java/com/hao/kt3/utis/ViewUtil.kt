package com.hao.kt3.utis

import android.content.res.Resources

/**
 * @author Yang Shihao
 * @date 2018/10/16
 */

object ViewUtil {

    fun pxToDp(px: Float): Float {
        var densityDpi = Resources.getSystem().displayMetrics.densityDpi.toFloat()
        return px / (densityDpi / 160)

    }

    fun dpToPx(dp: Int): Int {
        var density = Resources.getSystem().displayMetrics.density
        return Math.round(dp * density)
    }

}
