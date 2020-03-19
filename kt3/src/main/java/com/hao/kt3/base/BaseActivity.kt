package com.hao.kt3.base

import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v4.util.LongSparseArray
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import butterknife.ButterKnife
import butterknife.Unbinder
import com.hao.kt3.App
import com.hao.kt3.inject.component.ActivityComponent
import com.hao.kt3.inject.component.ConfigPersistentComponent
import com.hao.kt3.inject.component.DaggerConfigPersistentComponent
import com.hao.kt3.inject.module.ActivityModule
import java.util.concurrent.atomic.AtomicLong

/**
 * @author Yang Shihao
 * @date 2018/10/18
 */
abstract class BaseActivity : AppCompatActivity() {

    var activityComponent: ActivityComponent? = null
    var activityId = 0L
    var unbind: Unbinder? = null

    companion object {
        private val KEY_ACTIVITY_ID = "KEY_ACTIVITY_ID"
        private val NEXT_ID = AtomicLong(0)
        private val componentArray = LongSparseArray<ConfigPersistentComponent>()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
        unbind = ButterKnife.bind(this)

        activityId = savedInstanceState?.getLong(KEY_ACTIVITY_ID) ?: NEXT_ID.getAndIncrement()
        val configPersistentComponent: ConfigPersistentComponent?
        if (componentArray.get(activityId) == null) {
            configPersistentComponent = DaggerConfigPersistentComponent.builder()
                    .appComponent(App[this].component)
                    .build()


            componentArray.put(activityId, configPersistentComponent)

        } else {
            configPersistentComponent = componentArray.get(activityId)
        }
        activityComponent = configPersistentComponent?.activityComponent(ActivityModule(this))
        activityComponent?.inject(this)
        init()

    }

    override fun onDestroy() {
        super.onDestroy()
        if (!isChangingConfigurations) {
            componentArray.remove(activityId)
        }
        unbind?.unbind()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putLong(KEY_ACTIVITY_ID, activityId)
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        android.R.id.home -> {
            finish()
            true
        }

        else -> super.onOptionsItemSelected(item)
    }

    fun activityComponent() = activityComponent as ActivityComponent

    @LayoutRes
    abstract fun getLayoutId(): Int

    abstract fun init()
}