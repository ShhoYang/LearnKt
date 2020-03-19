package com.hao.kt3.ui.main

import com.hao.kt3.base.MvpView

/**
 * @author Yang Shihao
 * @date 2018/10/18
 */
interface MainMvpView : MvpView {
    fun showPokemon(pokemon: List<String>)

    fun showProgress(show: Boolean)

    fun showError(error: Throwable)
}