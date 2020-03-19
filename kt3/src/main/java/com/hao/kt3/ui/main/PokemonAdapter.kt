package com.hao.kt3.ui.main

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.hao.kt3.R
import javax.inject.Inject

/**
 * @author Yang Shihao
 * @date 2018/10/24
 */
class PokemonAdapter @Inject constructor() : RecyclerView.Adapter<PokemonAdapter.PokemonViewHolder>() {

    private var pokemonList: List<String>

    private var clickListener: ClickListener? = null

    init {
        pokemonList = emptyList()
    }

    fun setPokemon(pokenmon: List<String>) {
        pokemonList = pokenmon
    }

    fun setClicklistener(clickListener: ClickListener) {
        this.clickListener = clickListener
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): PokemonViewHolder {
        val view = LayoutInflater.from(p0.context).inflate(R.layout.item_pokemon, p0, false)
        return PokemonViewHolder(view)
    }

    override fun getItemCount(): Int {
        return pokemonList.size
    }

    override fun onBindViewHolder(p0: PokemonViewHolder, p1: Int) {
        p0.bind(pokemonList[p1])
    }

    interface ClickListener {
        fun onPokemonClick(pokenmon: String)
    }


    inner class PokemonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        lateinit var selectedPokemon: String

        @BindView(R.id.pokemon_name)
        @JvmField
        var pokemonName: TextView? = null

        init {
            ButterKnife.bind(this, itemView)
            itemView.setOnClickListener {

            }
        }

        fun bind(pokenmon: String) {
            selectedPokemon = pokenmon
            pokemonName?.text = String.format("%s%s", pokenmon.substring(0, 1).toUpperCase(), pokenmon.substring(1))
        }
    }
}