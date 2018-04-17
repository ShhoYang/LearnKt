package com.hao.learnkt.down

import okhttp3.Interceptor
import okhttp3.Response

/**
 * @author Yang Shihao
 */
class ProgressInterceptor(private val progressListener: ProgressListener) : Interceptor {

    @Throws(Exception::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val proceed = chain.proceed(chain.request())
        return proceed.newBuilder().body(ProgressResponseBody(proceed.body()!!, progressListener)).build()
    }
}