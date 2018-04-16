package com.hao.learnkt.common

import android.app.Application

/**
 * @author Yang Shihao
 */
class App : Application() {

    companion object {
        var instance: App by notNullSingleValue()
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}