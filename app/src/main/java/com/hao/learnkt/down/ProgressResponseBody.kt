package com.hao.learnkt.down

import okhttp3.MediaType
import okhttp3.ResponseBody
import okio.*

/**
 * @author Yang Shihao
 */
internal class ProgressResponseBody(private val responseBody: ResponseBody, private val progressListener: ProgressListener?) : ResponseBody() {

    private var mBufferedSorce: BufferedSource? = null

    override fun contentLength(): Long {
        return responseBody.contentLength()
    }

    override fun contentType(): MediaType? {
        return responseBody.contentType()
    }

    override fun source(): BufferedSource {
        if (mBufferedSorce == null) {
            mBufferedSorce = Okio.buffer(source(responseBody.source()))
        }
        return mBufferedSorce!!
    }

    private fun source(source: Source): Source {
        return object : ForwardingSource(source) {
            internal var totalReadBytes: Long = 0
            override fun read(sink: Buffer?, byteCount: Long): Long {

                val readBytes = super.read(sink, byteCount)
                totalReadBytes += if (readBytes != -1L) readBytes else 0
                progressListener?.onProgress(totalReadBytes,responseBody.contentLength(),readBytes == -1L)
                return readBytes
            }
        }
    }

}