package com.hao.kt3.model

data class Pokemon(
        val id: String,
        val name: String,
        val sprites: Sprites,
        val stats: List<Statistic>)