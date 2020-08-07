package com.dartharrmi.resipi.domain

import android.os.Parcelable
import com.dartharrmi.resipi.webservice.utils.EMPTY_STRING
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Ingredient(
    val id: Long = 0,
    var image: String = EMPTY_STRING,
    val name: String = EMPTY_STRING,
    val originalString: String = EMPTY_STRING,
    val amount: Double = 0.0,
    val unit: String = EMPTY_STRING
) : Parcelable
