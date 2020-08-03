package com.dartharrmi.resipi.domain

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Recipe(
        val id: Long,
        val title: String,
        val readyInMinutes: Int,
        val servings: Int,
        val sourceUrl: String,
        var image: String,
        val vegetarian: Boolean,
        val vegan: Boolean,
        val dairyFree: Boolean,
        val glutenFree: Boolean,
        val summary: String,
        val nutrition: Nutrition,
        var ingredients: List<Ingredient> = emptyList(),
        val instructions: List<InstructionStep>
): Parcelable