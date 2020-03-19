package com.hao.kt3.data

import com.hao.kt3.model.Pokemon
import com.hao.kt3.model.PokemonListResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * @author Yang Shihao
 * @date 2018/10/18
 */
interface Api {

    @GET("pokemon")
    fun getPokemonList(@Query("limit") limit: Int): Single<PokemonListResponse>

    @GET("pokemon/{name}")
    fun getPokemon(@Query("name") name: String): Single<Pokemon>
}