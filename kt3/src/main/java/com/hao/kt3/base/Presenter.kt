package com.hao.kt3.base

/**
 * @author Yang Shihao
 * @date 2018/10/18
 */
interface Presenter<in V : MvpView> {

    fun attachView(mvpView: V)

    fun detachView()
}