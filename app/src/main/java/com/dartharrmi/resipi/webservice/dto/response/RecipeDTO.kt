package com.dartharrmi.resipi.webservice.dto.response

import com.dartharrmi.resipi.domain.Recipe
import com.dartharrmi.resipi.webservice.utils.EMPTY_STRING

data class RecipeDTO(
    val id: Long?,
    val title: String?,
    val readyInMinutes: Int?,
    val servings: Int?,
    val sourceUrl: String?,
    val image: String?
)

fun RecipeDTO.toDomain(baseUri: String = EMPTY_STRING) = Recipe(
    id = this.id ?: 0,
    title = this.title.orEmpty(),
    readyInMinutes = this.readyInMinutes ?: 0,
    servings = this.servings ?: 0,
    sourceUrl = "$baseUri${this.sourceUrl.orEmpty()}",
    image = this.image.orEmpty()
)