package com.hao.learnkt.ui.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hao.learnkt.R
import com.hao.learnkt.adapter.PagerAdapter
import kotlinx.android.synthetic.main.fragment_text.*

/**
 * @author Yang Shihao
 */
class PicFragment : Fragment() {

    val TABS = arrayListOf("大胸妹", "小清新", "文艺范", "性感妹", "大长腿", "黑丝袜", "小翘臀")
    val TYPES = arrayListOf(34, 35, 36, 37, 38, 39, 40)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_text, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fragments: MutableList<Fragment> = ArrayList()
        for (i in TABS.indices) {
            fragments.add(ClassifyPicFragment.newInstance(TYPES[i]))
        }

        tabLayout.setupWithViewPager(viewPager)
        viewPager.adapter = PagerAdapter(childFragmentManager, fragments, TABS)
    }
}
