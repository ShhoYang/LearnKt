package com.hao.learnkt.down

import com.hao.learnkt.common.App
import com.hao.learnkt.utils.MD5Util
import okhttp3.*
import okio.Okio
import java.io.File
import java.io.IOException

/**
 * @author Yang Shihao
 */
object ProgressDownload {

    private var mProgressListener: ProgressListener? = null

    private val mListener: ProgressListener = object : ProgressListener {

        override fun onProgress(readBytes: Long, totalBytes: Long, done: Boolean) {
            if (mProgressListener != null) {
                mProgressListener!!.onProgress(readBytes, totalBytes, done)
            }
        }

        override fun onSave(path: String) {

        }
    }

    private val mClient: OkHttpClient by lazy {
        OkHttpClient.Builder().addNetworkInterceptor(ProgressInterceptor(mListener)).build()
    }

    fun downPic(url: String, progressListener: ProgressListener) {
        val existsFilePath: String? = exists(url)
        if (existsFilePath != null) {
            progressListener.onSave(existsFilePath)
            readLine()
        }
        mProgressListener = progressListener
        var request = Request.Builder().url(url).build()
        mClient.newCall(request).enqueue(object : Callback {
            @Throws(IOException::class)
            override fun onResponse(call: Call?, response: Response) {
                val file = File(App.instance.cacheDir, MD5Util.getHashKey(url))
                val sink = Okio.buffer(Okio.sink(file))
                val source = response.body()!!.source()
                sink.writeAll(source)
                sink.flush()
                progressListener.onSave(file.absolutePath)
            }

            override fun onFailure(call: Call?, e: IOException?) {
            }
        })
    }

    private fun exists(url: String): String? {
        val file = File(App.instance.cacheDir, MD5Util.getHashKey(url))
        return if (file.exists()) file.absolutePath else null
    }
}