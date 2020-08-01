package com.dartharrmi.resipi.domain

data class NutritionFact(
    val title: String,
    val amount: Double,
    val unit: String,
    val percentOfDailyNeeds: Double
)