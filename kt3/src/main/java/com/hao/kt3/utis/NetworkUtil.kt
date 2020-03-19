package com.hao.kt3.utis

import android.content.Context
import android.net.ConnectivityManager
import retrofit2.HttpException

/**
 * @author Yang Shihao
 * @date 2018/10/16
 */
object NetworkUtil {

    fun isHttpStatusCode(throwable: Throwable, statusCode: Int): Boolean {
        return throwable is HttpException && throwable.code() == statusCode;
    }

    fun isNetworkConnected(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.activeNetworkInfo
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting
    }
}