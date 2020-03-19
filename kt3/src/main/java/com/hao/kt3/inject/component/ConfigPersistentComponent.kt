package com.hao.kt3.inject.component

import com.hao.kt3.inject.ConfigPersistent
import com.hao.kt3.inject.module.ActivityModule
import com.hao.kt3.inject.module.FragmentModule
import dagger.Component

/**
 * @author Yang Shihao
 * @date 2018/10/23
 */
@ConfigPersistent
@Component(dependencies = [AppComponent::class])
interface ConfigPersistentComponent {

    fun activityComponent(activityModule: ActivityModule): ActivityComponent

    fun fragmentComponent(fragmentModule: FragmentModule): FragmentComponent
}