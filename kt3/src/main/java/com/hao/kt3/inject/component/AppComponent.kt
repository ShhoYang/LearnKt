package com.hao.kt3.inject.component

import android.app.Application
import android.content.Context
import com.hao.kt3.data.Api
import com.hao.kt3.data.DataManager
import com.hao.kt3.inject.ApplicationContext
import com.hao.kt3.inject.module.AppModule
import dagger.Component
import javax.inject.Singleton

/**
 * @author Yang Shihao
 * @date 2018/10/23
 */
@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {

    @ApplicationContext
    fun context(): Context

    fun application(): Application

    fun dataManager(): DataManager

    fun api(): Api
}