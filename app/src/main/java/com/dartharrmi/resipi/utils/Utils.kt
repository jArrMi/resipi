package com.dartharrmi.resipi.utils

import android.content.Context
import android.text.Spanned
import androidx.core.text.HtmlCompat
import com.dartharrmi.resipi.R

object Utils {

    /**
     * Format the serving size number to more readable format.
     */
    fun formatServings(servings: Int, context: Context) =
        context.getString(R.string.item_recipe_serving, servings)

    /**
     * Format the cooking time to more readable format.
     */
    fun formatReadyTime(readyTime: Int, context: Context) = context.getString(
        R.string.item_recipe_ready_time, parseMinutes(readyTime)
    )

    /**
     * Another format for the cooking time
     */
    fun parseMinutes(totalMinutes: Int): String {
        val hours = totalMinutes / 60
        val minutes = totalMinutes % 60

        val hoursFormatted = if (hours < 10) "0${hours}" else "$hours"
        val minutesFormatted = if (minutes < 10) "0${minutes}" else "$minutes"
        return "$hoursFormatted:$minutesFormatted"
    }

    /**
     * Parses the HTML sent in the summary
     */
    fun formatSummary(summary: String): Spanned =
        HtmlCompat.fromHtml(summary, HtmlCompat.FROM_HTML_MODE_LEGACY)

    const val SIZE_90_X_90 = "90x90"
    const val SIZE_100_X_100 = "100x100"
    const val SIZE_240_X_150 = "240x150"
    const val SIZE_250_X_250 = "250x250"
    const val SIZE_312_X_150 = "312x150"
    const val SIZE_480_X_360 = "480x360"
    const val SIZE_500_X_500 = "500x500"
    const val SIZE_636_X_393 = "636x393"

    private const val PREFIX_INGREDIENT_URL = "https://spoonacular.com/cdn/ingredients_"
    private const val PREFIX_RECIPE_URL = "https://spoonacular.com/recipeImages/"

    /**
     * Returns the URL for getting an ingredient's image.
     *
     * @param [sizeReplacement]     The preferred size for the image.
     * @param [ingredientFile]      The name of the image file returned by Spoonacular.
     */
    fun getIngredientUrl(sizeReplacement: String = SIZE_250_X_250, ingredientFile: String) =
        "$PREFIX_INGREDIENT_URL$sizeReplacement/$ingredientFile"

    /**
     * Returns the URL for getting a recipe's image.
     *
     * @param [recipeId]    The id of the recipe.
     * @param [size]        The preferred size for the image.
     */
    fun getRecipeUrl(recipeId: Long, size: String) = "$PREFIX_RECIPE_URL$recipeId-$size"

}