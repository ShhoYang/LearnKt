package com.hao.kt3.data

import android.content.Context
import android.content.SharedPreferences

/**
 * @author Yang Shihao
 * @date 2018/10/18
 */
class PreferencesHelper(context: Context) {
    private val preferenes: SharedPreferences

    companion object {
        val FILE_NAME = "PREFERENCE"
    }

    init {
        preferenes = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)
    }

    fun lear() {
        preferenes.edit().clear().apply()
    }
}