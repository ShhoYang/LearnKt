package com.hao.learnkt.utils

import java.security.MessageDigest

/**
 * @author Yang Shihao
 */
object MD5Util {

    fun getHashKey(key: String): String {
        var cacheKey: String
        try {
            val mDigest = MessageDigest.getInstance("MD5")
            mDigest.update(key.toByteArray())
            cacheKey = bytes2HexString(mDigest.digest())
        } catch (e: Exception) {
            cacheKey = key.hashCode().toString()
        }
        return cacheKey
    }

    private fun bytes2HexString(bytes: ByteArray): String {
        val sb = StringBuffer()
        for (i in bytes.indices) {
            val hex = Integer.toHexString(0xFF and bytes[i].toInt())
            if (hex.length == 1) {
                sb.append('0')
            }
            sb.append(hex)
        }
        return sb.toString()
    }
}