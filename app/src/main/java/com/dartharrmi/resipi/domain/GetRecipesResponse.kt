package com.dartharrmi.resipi.domain

data class GetRecipesResponse(
    val results: List<Recipe>,
    val baseUri: String,
    val offset: Int,
    val number: Int,
    val totalResults: Long,
    val isStale: Boolean
)