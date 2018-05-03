package com.hao.kt2

import android.app.Application
import com.hao.kt2.extensions.DelegatesExt

/**
 * @author Yang Shihao
 */
class App : Application() {

    companion object {
        var instance: App by DelegatesExt.notNullSingleValue()
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}