package com.hao.kt2.extensions

import java.text.DateFormat
import java.util.*

/**
 * @author Yang Shihao
 */
fun Long.toDateString(format: Int = DateFormat.MEDIUM): String {
    val sdf = DateFormat.getDateInstance(format, Locale.getDefault())
    return sdf.format(this)
}
