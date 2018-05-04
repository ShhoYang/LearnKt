package com.hao.kt2.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.hao.kt2.R

class DetailsActivity : AppCompatActivity() {

    companion object {
        const val ID  ="DetailsActivity.id"
        const val CITY_NAME = "DetailsActivity.cityName"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
    }
}
