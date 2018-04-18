package com.hao.learnkt.ui.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hao.learnkt.R
import com.hao.learnkt.adapter.JokeAdapter
import com.yhao.model.bean.Joke
import kotlin.properties.Delegates

class JokeFragment : Fragment() {

    private var mDatas: MutableList<Joke> = ArrayList()
    private var mCurrentPage: Int = 1
    private var mLoading: Boolean by Delegates.observable(false) { _, _, new ->
       // refreshLayout.isRefreshing = new
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_gif, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        loadData()
    }

    private fun initView() {
       // recyclerView.layoutManager = LinearLayoutManager(context)
      //  recyclerView.adapter = JokeAdapter(mDatas)

    }

    private fun loadData() {

    }
}
