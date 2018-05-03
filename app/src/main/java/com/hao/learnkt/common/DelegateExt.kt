package com.hao.learnkt.common

import android.content.Context
import android.content.SharedPreferences
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * @author Yang Shihao
 */

fun <T> notNullSingleValue():
        ReadWriteProperty<Any?, T> = NotNullSingleValueVar()


fun <T : Any> preference(context: Context, key: String, default: T):
        ReadWriteProperty<Any?, T> = Preference(context, key, default)


private class NotNullSingleValueVar<T> : ReadWriteProperty<Any?, T> {
    private var value: T? = null

    override fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return value ?: throw IllegalStateException("not initialized")
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        if (this.value == null) {
            this.value = value
        } else {
            throw IllegalStateException("already initialized")
        }
    }
}

private class Preference<T>(val context: Context, val key: String, val default: T) : ReadWriteProperty<Any?, T> {

    val pref: SharedPreferences by lazy {
        context.getSharedPreferences("sharedPre", Context.MODE_PRIVATE)
    }

    override fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return getPreference(pref, key, default)
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        putPreference(pref, key, value)
    }

    private fun <T> getPreference(pref: SharedPreferences, key: String, default: T): T = with(pref) {
        val value: Any? = when (default) {
            is String -> getString(key, default)
            is Int -> getInt(key, default)
            is Boolean -> getBoolean(key, default)
            is Float -> getFloat(key, default)
            else -> throw IllegalAccessException("this type can not saved into preference")
        }
        return value as T
    }

    private fun <T> putPreference(pref: SharedPreferences, key: String, value: T) = with(pref.edit()) {
        when (value) {
            is String -> putString(key, value)
            is Int -> putInt(key, value)
            is Boolean -> putBoolean(key, value)
            is Float -> putFloat(key, value)
            else -> throw IllegalAccessException("this type can not saved into preference")
        }.apply()
    }
}