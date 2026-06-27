package com.example.goliapp.domain.model

data class League(
    val id: Int,
    val name: String,
    val logo: String,
    val country: String,
    val flag: String?,
    val season: Int
)
