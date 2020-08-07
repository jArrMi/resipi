package com.dartharrmi.resipi.domain

import android.os.Parcelable
import com.dartharrmi.resipi.webservice.utils.EMPTY_STRING
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Recipe(
    val id: Long = 0,
    val title: String = EMPTY_STRING,
    val readyInMinutes: Int = 0,
    val servings: Int = 0,
    val sourceUrl: String = EMPTY_STRING,
    var image: String = EMPTY_STRING,
    val vegetarian: Boolean = false,
    val vegan: Boolean = false,
    val dairyFree: Boolean = false,
    val glutenFree: Boolean = false,
    val summary: String = EMPTY_STRING,
    val nutrition: Nutrition = Nutrition(),
    var ingredients: List<Ingredient> = emptyList(),
    val analyzedInstructions: List<InstructionStep> = emptyList()
) : Parcelable
