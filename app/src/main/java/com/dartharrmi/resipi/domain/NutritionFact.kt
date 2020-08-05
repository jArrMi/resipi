package com.dartharrmi.resipi.domain

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class NutritionFact(
    val title: String,
    val amount: Double,
    val unit: String,
    val percentOfDailyNeeds: Double
) : Parcelable
