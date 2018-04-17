package com.hao.learnkt.ui.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import com.hao.learnkt.R
import com.hao.learnkt.adapter.GifAdapter
import com.hao.learnkt.api.GifService
import com.hao.learnkt.ui.activity.showSnackbar
import com.yhao.model.bean.Gif
import kotlinx.android.synthetic.main.fragment_gif.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import kotlin.properties.Delegates

/**
 * @author Yang Shihao
 */
class GifFragment : Fragment() {

    private var mDatas: MutableList<Gif> = ArrayList()
    private var mCurrentPage: Int = 0
    private var mLoading by Delegates.observable(true) { _, _, new ->
        refreshLayout.isRefreshing = new
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_gif, container, false);
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = GifAdapter(mDatas, recyclerView)
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
            val data = GifService.getData(mCurrentPage)
            uiThread {
                mLoading = false
                if (data == null) {
                    showSnackbar(view as ViewGroup, "加载失败")
                    return@uiThread
                }

                if (mCurrentPage == 1) {
                    mDatas.clear()
                    mDatas.addAll(data)
                    recyclerView.adapter.notifyDataSetChanged()
                } else {
                    var src = mDatas.size
                    mDatas.clear()
                    mDatas.addAll(data)
                    recyclerView.adapter.notifyItemRangeInserted(src, data.size)
                }
            }
        }
    }
}
