package com.dartharrmi.resipi.domain

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dartharrmi.resipi.webservice.utils.EMPTY_STRING
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "recipes")
data class Recipe(
    @PrimaryKey val id: Long,
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
