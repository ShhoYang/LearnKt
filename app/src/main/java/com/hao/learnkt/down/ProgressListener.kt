package com.hao.learnkt.down

/**
 * @author Yang Shihao
 */
interface ProgressListener {

    fun onProgress(readBytes: Long, totalBytes: Long, done: Boolean)

    fun onSave(path: String)
}