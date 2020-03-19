package com.hao.kt3.utis.rx

import io.reactivex.*
import org.reactivestreams.Publisher

/**
 * @author Yang Shihao
 * @date 2018/10/24
 */
abstract class BaseScheduler<T> protected constructor(private val subscribeOnScheduler: Scheduler, private val observeOnScheduler: Scheduler) :
        ObservableTransformer<T, T>, SingleTransformer<T, T>, MaybeTransformer<T, T>, CompletableTransformer, FlowableTransformer<T, T> {

    override fun apply(upstream: Observable<T>): ObservableSource<T> {
        return upstream.subscribeOn(subscribeOnScheduler)
                .observeOn(observeOnScheduler)
    }

    override fun apply(upstream: Single<T>): SingleSource<T> {
        return upstream.subscribeOn(subscribeOnScheduler)
                .observeOn(observeOnScheduler)
    }

    override fun apply(upstream: Maybe<T>): MaybeSource<T> {
        return upstream.subscribeOn(subscribeOnScheduler)
                .observeOn(observeOnScheduler)
    }

    override fun apply(upstream: Completable): CompletableSource {
        return upstream.subscribeOn(subscribeOnScheduler)
                .observeOn(observeOnScheduler)
    }

    override fun apply(upstream: Flowable<T>): Publisher<T> {
        return upstream.subscribeOn(subscribeOnScheduler)
                .observeOn(observeOnScheduler)
    }
}