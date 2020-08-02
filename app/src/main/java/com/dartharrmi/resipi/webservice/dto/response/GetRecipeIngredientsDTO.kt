package com.dartharrmi.resipi.webservice.dto.response

import com.dartharrmi.resipi.domain.GetRecipeIngredientsResponse
import com.dartharrmi.resipi.webservice.dto.IngredientDTO
import com.dartharrmi.resipi.webservice.dto.toDomain
import com.google.gson.annotations.SerializedName

data class GetRecipeIngredientsDTO(
    @SerializedName("extendedIngredients") val ingredients: List<IngredientDTO>?
)

fun GetRecipeIngredientsDTO.toDomain() = GetRecipeIngredientsResponse(
    ingredients = this.ingredients?.map { it.toDomain() } ?: emptyList()
)