package com.hao.learnkt.ui.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hao.learnkt.R
import com.hao.learnkt.adapter.JokeAdapter
import com.hao.learnkt.api.JokeService
import com.hao.learnkt.ui.activity.showSnackbar
import com.yhao.model.bean.Joke
import kotlinx.android.synthetic.main.fragment_gif.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import kotlin.properties.Delegates

class JokeFragment : Fragment() {

    private var mDatas: MutableList<Joke> = ArrayList()
    private var mCurrentPage: Int = 1
    private var mLoading: Boolean by Delegates.observable(false) { _, _, new ->
        refreshLayout.isRefreshing = new
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
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = JokeAdapter(mDatas)
        recyclerView.setOnTouchListener { _, _ ->
            if (!mLoading && !recyclerView.canScrollVertically(1)) {
                mCurrentPage++
                loadData()
            }
            false
        }

        refreshLayout.setColorSchemeResources(R.color.colorPrimary)
        refreshLayout.setOnRefreshListener {
            mCurrentPage = 1
            loadData()
        }

    }

    private fun loadData() {
        mLoading = true
        doAsync {
            val data = JokeService.getData(mCurrentPage)
            uiThread {
                mLoading = false
                if (data == null) {
                    showSnackbar(view as ViewGroup, "加载失败")
                } else if (mCurrentPage == 1) {
                    mDatas.clear()
                    mDatas.addAll(data)
                    recyclerView.adapter.notifyDataSetChanged()
                } else {
                    val src = mDatas.size
                    mDatas.addAll(data)
                    recyclerView.adapter.notifyItemRangeInserted(src, data.size)
                }
            }
        }
    }
}
