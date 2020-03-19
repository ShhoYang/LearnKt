package com.hao.kt3.base

import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.ButterKnife
import butterknife.Unbinder

/**
 * @author Yang Shihao
 * @date 2018/10/18
 */
abstract class BaseFragment : Fragment() {

    var unbind: Unbinder? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        var rootView = inflater.inflate(getLayoutId(), container, false)
        unbind = ButterKnife.bind(rootView)
        return rootView
    }


    override fun onDestroyView() {
        super.onDestroyView()
        unbind?.unbind()
    }

    @LayoutRes
    abstract fun getLayoutId(): Int
}