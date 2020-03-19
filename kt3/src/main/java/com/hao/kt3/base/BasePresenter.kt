package com.hao.kt3.base

import io.reactivex.disposables.CompositeDisposable
import rx.CompletableSubscriber
import rx.Subscription
import rx.subscriptions.CompositeSubscription
import java.lang.RuntimeException

/**
 * @author Yang Shihao
 * @date 2018/10/18
 */
open class BasePresenter<T : MvpView> : Presenter<T> {

    var mvpView: T? = null
    private val compositeSubscription = CompositeSubscription()

    override fun attachView(mvpView: T) {
        this.mvpView = mvpView
    }

    override fun detachView() {
        mvpView = null
        if (!compositeSubscription.isUnsubscribed()) {
            compositeSubscription.clear()
        }
    }

    private val isViewAttached: Boolean
        get() = mvpView != null

    fun checkViewAttached() {
        if (!isViewAttached) throw Exception()
    }

    fun addSubscription(subscription: Subscription) {
        compositeSubscription.add(subscription)
    }

    private class MvpViewNotAttachedException internal constructor() :
            RuntimeException("Please call Presenter.attachView(MapView) before requesting data to the Presenter")
}