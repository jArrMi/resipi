package com.dartharrmi.resipi.webservice.dto

import com.dartharrmi.resipi.domain.Nutrition
import com.dartharrmi.resipi.domain.Recipe
import com.google.gson.annotations.SerializedName

data class RecipeDTO(
        val id: Long?,
        val title: String?,
        val readyInMinutes: Int?,
        val servings: Int?,
        val sourceUrl: String?,
        val image: String?,
        val vegetarian: Boolean?,
        val vegan: Boolean?,
        val dairyFree: Boolean?,
        val glutenFree: Boolean?,
        val summary: String?,
        val nutrition: NutritionDTO?,
        @SerializedName("analyzedInstructions") val instructions: List<InstructionStepDTO>?
)

fun RecipeDTO.toDomain() = Recipe(
        id = this.id ?: 0,
        title = this.title.orEmpty(),
        readyInMinutes = this.readyInMinutes ?: 0,
        servings = this.servings ?: 0,
        sourceUrl = this.sourceUrl.orEmpty(),
        image = this.image.orEmpty(),
        vegetarian = this.vegetarian ?: false,
        vegan = this.vegan ?: false,
        dairyFree = this.dairyFree ?: false,
        glutenFree = this.glutenFree ?: false,
        summary = this.summary.orEmpty(),
        nutrition = this.nutrition?.toDomain() ?: Nutrition(),
        instructions = this.instructions?.map { it.toDomain() } ?: emptyList()
)