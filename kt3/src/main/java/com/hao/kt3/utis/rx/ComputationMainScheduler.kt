package com.hao.kt3.utis.rx

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * @author Yang Shihao
 * @date 2018/10/24
 */
class ComputationMainScheduler<T> private constructor():
        BaseScheduler<T>(Schedulers.computation(),AndroidSchedulers.mainThread())