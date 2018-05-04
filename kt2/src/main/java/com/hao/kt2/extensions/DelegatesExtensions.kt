package com.hao.kt2.extensions

import android.content.Context
import android.content.SharedPreferences
import kotlin.reflect.KProperty

/**
 * @author Yang Shihao
 */

object DelegatesExt {

    fun <T> notNullSingleValue() = NotNullSingleValueVar<T>()
    fun <T> preference(context: Context, key: String, value: T) = Preference<T>(context, key, value)
}

class NotNullSingleValueVar<T> {

    var value: T? = null

    operator fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return value ?: throw IllegalStateException("not initialized")
    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        this.value = if (this.value == null) value
        else throw IllegalStateException("${property.name} already initialized")
    }
}

class Preference<T>(private val context: Context, private val key: String, private val value: T) {

    private val prefs: SharedPreferences by lazy {
        context.getSharedPreferences("kt2", Context.MODE_PRIVATE)
    }

    operator fun getValue(thisRef: Any?, property: KProperty<*>): T = findPreference(key, value)

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        putPreference(key, value)
    }

    private fun findPreference(key: String, defaultValue: T): T = with(prefs) {
        val v = when (defaultValue) {
            is String -> getString(key, defaultValue)
            is Int -> getInt(key, defaultValue)
            is Long -> getLong(key, defaultValue)
            is Boolean -> getBoolean(key, defaultValue)
            else -> throw IllegalArgumentException("This type can not saved into preference")
        }

        return@with v as T
    }

    private fun putPreference(key: String, value: T) = with(prefs.edit()) {
        when (value) {
            is String -> putString(key, value)
            is Int -> putInt(key, value)
            is Long -> putLong(key, value)
            is Boolean -> putBoolean(key, value)
            else -> throw IllegalArgumentException("This type can not saved into preference")
        }.apply()
    }
}