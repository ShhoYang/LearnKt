package com.hao.kt3.view

import android.content.Context
import android.os.Build
import android.support.annotation.RequiresApi
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.LinearLayout
import butterknife.ButterKnife
import butterknife.OnClick
import com.hao.kt3.R

/**
 * @author Yang Shihao
 * @date 2018/10/18
 */
class ErrorView : LinearLayout {

    private var errorListener: ErrorListener? = null

    constructor(context: Context?) : super(context) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes) {
        init()
    }

    fun setErrorListener(errorListener: ErrorListener) {
        this.errorListener = errorListener
    }

    private fun init() {
        orientation = LinearLayout.VISIBLE
        gravity = Gravity.CENTER
        LayoutInflater.from(context).inflate(R.layout.view_error, this)
        ButterKnife.bind(this)
    }

    @OnClick(R.id.button_reload)
    fun onReloadButtonClicked() {
        errorListener?.let { errorListener?.onReloadData() }
    }

    interface ErrorListener {
        fun onReloadData()
    }

}