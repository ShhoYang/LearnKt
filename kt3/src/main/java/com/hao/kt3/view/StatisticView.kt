package com.hao.kt3.view

import android.content.Context
import android.os.Build
import android.support.annotation.RequiresApi
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.hao.kt3.R
import com.hao.kt3.model.Statistic

/**
 * @author Yang Shihao
 * @date 2018/10/18
 */
class StatisticView : RelativeLayout {

    @BindView(R.id.pokemon_name)
    @JvmField
    var nameText: TextView? = null


    @BindView(R.id.progress_stat)
    @JvmField
    var statProgress: ProgressBar? = null

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

    private fun init() {
        LayoutInflater.from(context).inflate(R.layout.view_statistic, this)

        ButterKnife.bind(this)
    }

    fun setStat(statistic: Statistic) {
        nameText?.text = "${statistic.stat?.name?.substring(0, 1)?.toUpperCase()}${statistic.stat?.name?.substring(1)}"
        statProgress?.progress = statistic.baseStat
    }

}