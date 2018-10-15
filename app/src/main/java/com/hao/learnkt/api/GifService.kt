package com.hao.learnkt.api

import android.util.Log
import com.google.gson.Gson
import com.hao.learnkt.common.Constant.Companion.buildUrl
import com.yhao.model.bean.Gif
import com.yhao.model.bean.GifResult
import java.net.URL

/**
 * @author Yang Shihao
 */
class GifService {

    companion object {

        val baseUrl = "http://route.showapi.com/341-3"
        private fun buildBaseUrl(page: Int, maxResult: Int): String {
            return buildUrl("$baseUrl?page=$page&maxResult=$maxResult")
        }

        fun getData(page: Int, maxResult: Int = 20): List<Gif>? {
            var json: String?
            try {
                json = URL(buildBaseUrl(page, maxResult)).readText()
            } catch (e: Exception) {
                return null
            }

            Log.d("1111qqqqqqqqq", json)
            val data = Gson().fromJson(json, GifResult::class.java)
            return data.showapi_res_body.contentlist
        }
    }
}