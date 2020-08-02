package com.dartharrmi.resipi.webservice.dto.response

import com.dartharrmi.resipi.domain.GetRecipesResponse
import com.dartharrmi.resipi.webservice.dto.RecipeDTO
import com.dartharrmi.resipi.webservice.dto.toDomain

data class GetRecipesResponseDTO(
    val results: List<RecipeDTO>?,
    val baseUri: String?,
    val offset: Int?,
    val number: Int?,
    val totalResults: Long?,
    val isStale: Boolean?
)

fun GetRecipesResponseDTO.toDomain() = GetRecipesResponse(
    results = this.results?.map { it.toDomain() } ?: emptyList(),
    baseUri = this.baseUri.orEmpty(),
    offset = this.offset ?: 0,
    number = this.number ?: 0,
    totalResults = this.totalResults ?: 0,
    isStale = this.isStale ?: false
)