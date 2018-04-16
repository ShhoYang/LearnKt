package com.hao.learnkt.ui.activity

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.KeyEvent
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import com.hao.learnkt.R
import com.hao.learnkt.common.preference
import com.hao.learnkt.ui.fragment.GifFragment
import com.hao.learnkt.ui.fragment.PicFragment
import com.hao.learnkt.ui.fragment.TextFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.properties.Delegates

class MainActivity : AppCompatActivity() {

    val mFragments: Array<Fragment> = arrayOf(TextFragment(), PicFragment(), GifFragment())


    var mDefaultIndex: Int by preference(this, "fragmentIndex", 0)

    var mCurrentIndex: Int by Delegates.observable(0) { _, _, new ->
        navigationView.setCheckedItem(when (new) {
            0 -> R.id.nav_text
            1 -> R.id.nav_pic
            2 -> R.id.nav_gif
            else -> R.id.nav_text
        })
    }

    var mBackPressedTime by Delegates.observable(0L) { _, old, new ->
        if (new - old > 1000) {
            showSnackbar(drawerLayout, getString(R.string.exit_message))
        }

        if (new - old in 1..1000) {
            mDefaultIndex = mCurrentIndex;
            finish()
        }
    }

    var mMenuOpen: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()
    }

    private fun initView() {
        setSupportActionBar(toolBar)
        val toggle = ActionBarDrawerToggle(this,
                drawerLayout,
                toolBar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        drawerLayout.addDrawerListener(object : DrawerLayout.SimpleDrawerListener() {
            override fun onDrawerClosed(drawerView: View) {
                super.onDrawerClosed(drawerView)
                mMenuOpen = false
            }

            override fun onDrawerOpened(drawerView: View) {
                super.onDrawerOpened(drawerView)
                mMenuOpen = true
            }

        })

        navigationView.setNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_text -> switchFragment(0, item)
                R.id.nav_pic -> switchFragment(1, item)
                R.id.nav_gif -> switchFragment(2, item)
                else -> false
            }
        }

    }

    private fun switchFragment(index: Int, menu: MenuItem): Boolean {
        if (index != mCurrentIndex) {
            var beginTransaction = supportFragmentManager.beginTransaction()
            if (!mFragments[index].isAdded) {
                beginTransaction.add(R.id.frameLayout, mFragments[index])
                beginTransaction.show(mFragments[index])
            }

            beginTransaction.hide(mFragments[mCurrentIndex])
            mCurrentIndex = index
            beginTransaction.commitAllowingStateLoss()
            toolBar.title = menu.title
            menu.isChecked = true
        }
        drawerLayout.closeDrawers()
        return true
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            mBackPressedTime = if (mMenuOpen) {
                drawerLayout.closeDrawers()
                mBackPressedTime
            } else {
                System.currentTimeMillis()
            }
        }
        return super.onKeyDown(keyCode, event)
    }

    private fun showSnackbar(viewGroup: ViewGroup, text: String, duration: Int = 1000) {
        val snackbar = Snackbar.make(viewGroup, text, duration)
        snackbar.view.setBackgroundColor(ContextCompat.getColor(viewGroup.context, R.color.colorPrimary))
        snackbar.show()
    }
}
