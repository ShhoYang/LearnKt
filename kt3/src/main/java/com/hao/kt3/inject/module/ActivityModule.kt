package com.hao.kt3.inject.module

import android.app.Activity
import android.content.Context
import com.hao.kt3.inject.ActivityContext
import dagger.Module
import dagger.Provides

/**
 * @author Yang Shihao
 * @date 2018/10/23
 */
@Module
class ActivityModule(private val activity: Activity) {

    @Provides
    internal fun provideActivity(): Activity {
        return activity
    }

    @Provides
    @ActivityContext
    internal fun provideContext(): Context {
        return activity
    }
}