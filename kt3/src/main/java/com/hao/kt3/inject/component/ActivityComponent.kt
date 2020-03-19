package com.hao.kt3.inject.component

import com.hao.kt3.base.BaseActivity
import com.hao.kt3.inject.PreActivity
import com.hao.kt3.inject.module.ActivityModule
import com.hao.kt3.ui.main.MainActivity
import dagger.Subcomponent

/**
 * @author Yang Shihao
 * @date 2018/10/23
 */
@PreActivity
@Subcomponent(modules = [ActivityModule::class])
interface ActivityComponent {

    fun inject(baseActivity: BaseActivity)

    fun inject(mainActivity: MainActivity)

}