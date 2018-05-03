package com.hao.kt2.ui

import android.os.Build
import android.support.annotation.RequiresApi
import android.widget.Toolbar
import com.hao.kt2.R

/**
 * @author Yang Shihao
 */
@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
interface ToolbarManger {
    var toolbar: Toolbar
    var toolbarTitle: String
        get() = toolbar.title.toString()
        set(value) {
            toolbar.title = value
        }

    fun initToolbar(){
        toolbar.inflateMenu(R.menu.menu_main)
        toolbar.setOnMenuItemClickListener {
            when(it.itemId){
            }
            return@setOnMenuItemClickListener true
        }
    }

}