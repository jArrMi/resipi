package com.dartharrmi.resipi.domain

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Nutrition(
        val nutritionFacts: List<NutritionFact> = emptyList(),
        val caloricBreakdown: CaloricBreakdown = CaloricBreakdown()
): Parcelable

@Parcelize
data class CaloricBreakdown(
        val percentProtein: Double = 0.0,
        val percentFat: Double = 0.0,
        val percentCarbs: Double = 0.0
): Parcelable
