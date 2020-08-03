package com.dartharrmi.resipi.domain

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Ingredient(
        val id: Long,
        var image: String,
        val name: String,
        val originalString: String,
        val amount: Double,
        val unit: String
): Parcelable