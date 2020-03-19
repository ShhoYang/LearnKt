package com.hao.kt3.inject.component

import com.hao.kt3.inject.PreFragment
import com.hao.kt3.inject.module.FragmentModule
import dagger.Subcomponent

/**
 * @author Yang Shihao
 * @date 2018/10/23
 */
@PreFragment
@Subcomponent(modules = [FragmentModule::class])
interface FragmentComponent