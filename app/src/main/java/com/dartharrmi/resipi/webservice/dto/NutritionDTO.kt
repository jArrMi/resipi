package com.dartharrmi.resipi.webservice.dto

import com.dartharrmi.resipi.domain.CaloricBreakdown
import com.dartharrmi.resipi.domain.Nutrition
import com.google.gson.annotations.SerializedName

data class NutritionDTO(
        @SerializedName("nutrients") val nutritionFacts: List<NutritionFactDTO>?,
        val caloricBreakdown: CaloricBreakdownDTO?
)

data class CaloricBreakdownDTO(
        val percentProtein: Double?,
        val percentFat: Double?,
        val percentCarbs: Double?
)

fun NutritionDTO.toDomain() = Nutrition(
        nutritionFacts = this.nutritionFacts?.map { it.toDomain() } ?: emptyList(),
        caloricBreakdown = this.caloricBreakdown?.toDomain() ?: CaloricBreakdown()
)

fun CaloricBreakdownDTO.toDomain() = CaloricBreakdown(
        percentProtein = this.percentProtein ?: 0.0,
        percentCarbs = this.percentCarbs ?: 0.0,
        percentFat = this.percentFat ?: 0.0
)