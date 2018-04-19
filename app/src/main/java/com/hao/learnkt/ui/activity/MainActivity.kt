package com.hao.learnkt.ui.activity

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.text.format.Formatter
import android.view.KeyEvent
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.hao.learnkt.R
import com.hao.learnkt.common.App
import com.hao.learnkt.common.preference
import com.hao.learnkt.ui.fragment.GifFragment
import com.hao.learnkt.ui.fragment.PicFragment
import com.hao.learnkt.ui.fragment.TextFragment
import com.hao.learnkt.utils.FileUtil
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.io.File
import kotlin.properties.Delegates

fun showSnackbar(viewGroup: ViewGroup, text: String, duration: Int = 1000) {
    val snackbar = Snackbar.make(viewGroup, text, duration)
    snackbar.view.setBackgroundColor(ContextCompat.getColor(viewGroup.context, R.color.colorPrimary))
    snackbar.show()
}


class MainActivity : AppCompatActivity() {

    private val mTitles: Array<String> = arrayOf("段子鸡汤", "花瓣福利", "动态搞笑图")
    private val mFragments: Array<Fragment> = arrayOf(TextFragment(), PicFragment(), GifFragment())

    private var mDefaultIndex: Int by preference(this, "fragmentIndex", 0)

    private var mCurrentIndex: Int by Delegates.observable(0) { _, _, new ->
        navigationView.setCheckedItem(when (new) {
            0 -> R.id.nav_text
            1 -> R.id.nav_pic
            2 -> R.id.nav_gif
            else -> R.id.nav_text
        })
    }

    private var mBackPressedTime: Long by Delegates.observable(0L) { _, old, new ->
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
                navigationView.menu.findItem(R.id.nav_clear).title = "清理缓存"
                mMenuOpen = false
            }

            override fun onDrawerOpened(drawerView: View) {
                super.onDrawerOpened(drawerView)
                mMenuOpen = true
                doAsync {
                    val glideCacheDir = Glide.getPhotoCacheDir(this@MainActivity) as File
                    val appCacheDir = App.instance.cacheDir
                    var size: Long = FileUtil.getFolderSize(glideCacheDir) + FileUtil.getFolderSize(appCacheDir)
                    var formatFileSize = Formatter.formatFileSize(this@MainActivity, size)
                    uiThread {
                        navigationView.menu.findItem(R.id.nav_clear).title = "清理缓存$formatFileSize"
                    }
                }
            }

        })

        navigationView.setNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_text -> switchFragment(0, item)
                R.id.nav_pic -> switchFragment(1, item)
                R.id.nav_gif -> switchFragment(2, item)
                R.id.nav_clear -> clearCache(item)
                else -> false
            }
        }


        mCurrentIndex = mDefaultIndex
        supportFragmentManager.beginTransaction().replace(R.id.frameLayout, mFragments[mCurrentIndex]).commit()
    }


    override fun onResume() {
        super.onResume()
        toolBar.title = mTitles[mCurrentIndex]
    }

    private fun switchFragment(index: Int, menu: MenuItem): Boolean {
        if (index != mCurrentIndex) {
            var beginTransaction = supportFragmentManager.beginTransaction()
            if (!mFragments[index].isAdded) {
                beginTransaction.add(R.id.frameLayout, mFragments[index])

            }

            beginTransaction.show(mFragments[index]).hide(mFragments[mCurrentIndex]).commitAllowingStateLoss()
            mCurrentIndex = index
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
        return true
    }

    private fun clearCache(menu: MenuItem): Boolean {
        menu.title = "正在清理..."
        doAsync {
            val glideCacheDir = Glide.getPhotoCacheDir(this@MainActivity) as File
            var appCacheDir = App.instance.cacheDir
            var b1 = FileUtil.deleteFolder(glideCacheDir)
            var b2 = FileUtil.deleteFolder(appCacheDir)
            uiThread {
                showSnackbar(drawerLayout, "清理完成")
                menu.title = "清理完成"
            }
        }
        return true
    }
}
