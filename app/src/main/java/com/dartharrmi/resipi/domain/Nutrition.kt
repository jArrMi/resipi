package com.dartharrmi.resipi.domain

data class Nutrition(
    val nutritionFacts: List<NutritionFact> = emptyList()
)