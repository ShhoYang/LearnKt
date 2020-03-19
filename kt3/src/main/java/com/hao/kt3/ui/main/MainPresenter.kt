package com.hao.kt3.ui.main

import com.hao.kt3.base.BasePresenter
import com.hao.kt3.data.DataManager
import com.hao.kt3.inject.ConfigPersistent
import com.hao.kt3.utis.rx.SchedulerUtils
import javax.inject.Inject

/**
 * @author Yang Shihao
 * @date 2018/10/18
 */
@ConfigPersistent
class MainPresenter @Inject constructor(val dataManager: DataManager) : BasePresenter<MainMvpView>() {

    fun getMokemon(limit: Int) {
        checkViewAttached()
        mvpView?.showProgress(true)
        dataManager.getPokemonList(limit)
                .compose(SchedulerUtils.ioToMain())
                .subscribe({ pokemons ->
                    mvpView?.apply {
                        showProgress(false)
                        showPokemon(pokemons)
                    }
                }) { throwable ->
                    mvpView?.apply {
                        showProgress(false)
                        showError(throwable)
                    }
                }
    }
}