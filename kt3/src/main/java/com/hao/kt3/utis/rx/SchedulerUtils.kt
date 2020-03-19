package com.hao.kt3.utis.rx

/**
 * @author Yang Shihao
 * @date 2018/10/24
 */
object SchedulerUtils {
    fun <T> ioToMain(): IoMainScheduler<T> {
        return IoMainScheduler()
    }
}