package com.hao.learnkt.ui.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.VideoView
import com.hao.learnkt.R
import com.hao.learnkt.adapter.RhesisAdapter
import com.hao.learnkt.ui.activity.showSnackbar
import com.yhao.model.bean.Rhesis
import com.yhao.model.service.RhesisService
import kotlinx.android.synthetic.main.fragment_gif.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.util.ArrayList
import kotlin.properties.Delegates
import kotlin.reflect.KProperty

class RhesisFragment : Fragment() {

    private val mDatas: MutableList<Rhesis> = ArrayList()
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
        recyclerView.adapter = RhesisAdapter(mDatas)
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!mLoading && !this@RhesisFragment.recyclerView.canScrollVertically(1)) {
                    mCurrentPage++
                    loadData()
                }
            }
        })

        refreshLayout.setColorSchemeResources(R.color.colorPrimary)
        refreshLayout.setOnRefreshListener {
            mCurrentPage = 1
            loadData()
        }
    }

    private fun loadData() {
        mLoading = true
        doAsync {
            val data = RhesisService.getData(mCurrentPage)
            uiThread {
                mLoading = false
                if (data == null || data.isEmpty()) {
                    showSnackbar(view as ViewGroup, "加载失败")
                    return@uiThread
                }

                if (mCurrentPage == 1) {
                    mDatas.clear()
                    mDatas.addAll(data)
                    recyclerView.adapter?.notifyDataSetChanged()
                } else {
                    var src = mDatas.size
                    mDatas.addAll(data)
                    recyclerView.adapter?.notifyItemRangeInserted(src, data.size)
                }
            }
        }
    }
}
