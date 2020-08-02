package com.dartharrmi.resipi.webservice.dto

import com.dartharrmi.resipi.domain.Nutrition
import com.google.gson.annotations.SerializedName

data class NutritionDTO(
    @SerializedName("nutrients") val nutritionFacts: List<NutritionFactDTO>?
)

fun NutritionDTO.toDomain() = Nutrition(
    nutritionFacts = this.nutritionFacts?.map { it.toDomain() } ?: emptyList()
)