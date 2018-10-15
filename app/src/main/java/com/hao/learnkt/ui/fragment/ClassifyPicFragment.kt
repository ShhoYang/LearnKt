package com.hao.learnkt.ui.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hao.learnkt.R
import com.hao.learnkt.adapter.PicAdapter
import com.hao.learnkt.ui.activity.showSnackbar
import com.yhao.model.bean.Huaban
import com.yhao.model.service.HuabanService
import kotlinx.android.synthetic.main.fragment_gif.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import kotlin.properties.Delegates

/**
 * @author Yang Shihao
 */
class ClassifyPicFragment : Fragment() {

    private val mDatas: MutableList<Huaban> = ArrayList()
    private var mCurrentPage = 1
    private var mLoading: Boolean by Delegates.observable(false) { _, _, new ->
        refreshLayout.isRefreshing = new

    }
    private var mType: Int = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_gif, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mType = arguments!!.getInt(EXTRA_TYPE)
        initView()
        loadData()
    }

    private fun initView() {
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        recyclerView.adapter = PicAdapter(mDatas)
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
            val data = HuabanService.getData(mType, mCurrentPage)
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

    companion object {
        private val EXTRA_TYPE = "extra_type"
        fun newInstance(type: Int): ClassifyPicFragment {
            val bundle = Bundle()
            bundle.putInt(EXTRA_TYPE, type)
            val fragment = ClassifyPicFragment()
            fragment.arguments = bundle
            return fragment
        }
    }
}