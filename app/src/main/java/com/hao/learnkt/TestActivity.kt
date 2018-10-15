package com.hao.learnkt

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.activity_test.*

class TestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

//        val options :RequestOptions = RequestOptions().diskCacheStrategy(DiskCacheStrategy.RESOURCE)
//        Glide.with(this)
//                .load("http://www.zbjuran.com/uploads/allimg/180328/2-1P32Q50P3.gif")
//                .apply(options)
//                .into(imageView)
    }
}
