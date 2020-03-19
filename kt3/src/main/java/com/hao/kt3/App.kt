package com.hao.kt3

import android.content.Context
import android.support.multidex.MultiDexApplication
import com.hao.kt3.inject.component.AppComponent
import com.hao.kt3.inject.component.DaggerAppComponent
import com.hao.kt3.inject.module.AppModule
import com.hao.kt3.inject.module.NetworkModule

/**
 * @author Yang Shihao
 * @date 2018/10/16
 */
class App : MultiDexApplication() {

    private var appComponent: AppComponent? = null

    companion object {
        operator fun get(context: Context) = context.applicationContext as App
    }

    var component: AppComponent
        get() {
            if (appComponent == null) {
                appComponent = DaggerAppComponent.builder()
                        .appModule(AppModule(this))
                        .networkModule(NetworkModule(this))
                        .build()
            }

            return appComponent as AppComponent

        }
        set(value) {
            this.appComponent = value
        }
}