package com.hao.kt3.data

import com.hao.kt3.model.Pokemon
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @author Yang Shihao
 * @date 2018/10/18
 */
@Singleton
class DataManager @Inject constructor(private val api: Api) {

    fun getPokemonList(limit: Int): Single<List<String>> {
        return api.getPokemonList(limit)
                .toObservable()
                .flatMapIterable { (results) -> results }
                .map { (name) -> name }
                .toList()
    }

    fun getPokemon(name: String): Single<Pokemon> {
        return api.getPokemon(name)
    }
}
