package com.hao.kt3.ui.main

import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.Toast
import com.hao.kt3.R
import com.hao.kt3.base.BaseActivity
import com.hao.kt3.utis.TOAST
import com.hao.kt3.utis.gone
import com.hao.kt3.utis.visible
import com.hao.kt3.view.ErrorView
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : BaseActivity(), MainMvpView, PokemonAdapter.ClickListener, ErrorView.ErrorListener {


    @Inject
    lateinit var mainPresenter: MainPresenter

    @Inject
    lateinit var pokemonAdapter: PokemonAdapter


    companion object {
        private val POKEMON_COUNT = 20
    }

    override fun getLayoutId() = R.layout.activity_main

    override fun init() {
        activityComponent().inject(this)
        mainPresenter.attachView(this)
        setSupportActionBar(main_toolbar)
        swipeToRefresh?.apply {
            setProgressBackgroundColorSchemeResource(R.color.primary)
            setColorSchemeResources(R.color.white)
            setOnRefreshListener {
                mainPresenter.getMokemon(POKEMON_COUNT) }
        }

        pokemonAdapter.setClicklistener(this)
        recyclerPokemon?.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = pokemonAdapter
        }

        errorView?.setErrorListener(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        mainPresenter.detachView()
    }

    override fun showPokemon(pokemon: List<String>) {
        pokemonAdapter.apply {
            setPokemon(pokemon)
            notifyDataSetChanged()
        }
    }

    override fun showProgress(show: Boolean) {
        if (show) {
            if (recyclerPokemon?.visibility == View.VISIBLE && pokemonAdapter.itemCount > 0) {
                swipeToRefresh?.isRefreshing = true
            } else {
                progressBar?.visible()
                recyclerPokemon?.gone()
                swipeToRefresh?.gone()
            }

            viewError?.gone()
        } else {
            swipeToRefresh?.isRefreshing = false
            progressBar?.gone()
        }
    }

    override fun showError(error: Throwable) {
        recyclerPokemon?.gone()
        swipeToRefresh?.gone()
        viewError?.visible()
    }

    override fun onPokemonClick(pokenmon: String) {
        TOAST(pokenmon, Toast.LENGTH_SHORT)
    }

    override fun onReloadData() {
        mainPresenter.getMokemon(POKEMON_COUNT)

    }
}