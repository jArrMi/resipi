package com.dartharrmi.resipi.domain

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Nutrition(
    val nutritionFacts: List<NutritionFact> = emptyList()
) : Parcelable