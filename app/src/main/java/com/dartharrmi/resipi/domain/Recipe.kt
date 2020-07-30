package com.dartharrmi.resipi.domain

data class Recipe(
    val id: Long,
    val title: String,
    val readyInMinutes: Int,
    val servings: Int,
    val sourceUrl: String,
    val image: String
)