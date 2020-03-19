package com.hao.kt3.inject.module

import android.app.Activity
import android.content.Context
import android.support.v4.app.Fragment
import com.hao.kt3.inject.ActivityContext
import dagger.Module
import dagger.Provides

/**
 * @author Yang Shihao
 * @date 2018/10/23
 */
@Module
class FragmentModule(private val fragment: Fragment) {

    @Provides
    internal fun provideFragment(): Fragment {
        return fragment
    }

    @Provides
    internal fun provideActivity(): Activity? {
        return fragment.activity
    }

    @Provides
    @ActivityContext
    internal fun provideContext(): Context? {
        return fragment.context
    }
}