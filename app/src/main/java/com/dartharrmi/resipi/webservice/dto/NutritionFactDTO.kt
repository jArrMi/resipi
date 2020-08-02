package com.dartharrmi.resipi.webservice.dto

import com.dartharrmi.resipi.domain.NutritionFact

data class NutritionFactDTO(
    val title: String?,
    val amount: Double?,
    val unit: String?,
    val percentOfDailyNeeds: Double?
)

fun NutritionFactDTO.toDomain() = NutritionFact(
    title = this.title.orEmpty(),
    amount = this.amount ?: 0.0,
    unit = this.unit.orEmpty(),
    percentOfDailyNeeds = this.percentOfDailyNeeds ?: 0.0
)