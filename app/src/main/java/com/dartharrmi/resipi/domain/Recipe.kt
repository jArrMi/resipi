package com.dartharrmi.resipi.domain

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "recipes")
data class Recipe(
    @PrimaryKey val id: Long,
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
    val analyzedInstructions: List<InstructionStep>
) : Parcelable
