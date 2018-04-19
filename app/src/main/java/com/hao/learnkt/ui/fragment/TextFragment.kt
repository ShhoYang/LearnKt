package com.hao.learnkt.ui.fragment

import android.os.Bundle
import android.support.design.widget.TabLayout
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
class TextFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_text, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tabLayout.tabMode = TabLayout.MODE_FIXED
        tabLayout.setupWithViewPager(viewPager)
        viewPager.adapter = PagerAdapter(childFragmentManager, arrayListOf(JokeFragment(), RhesisFragment()), arrayListOf("搞笑段子", "励志鸡汤"))
    }
}
