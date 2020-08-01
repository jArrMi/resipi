package com.dartharrmi.resipi.domain

data class Ingredient(
    val id: Long,
    val image: String,
    val name: String,
    val originalString: String,
    val amount: Double,
    val unit: String
)