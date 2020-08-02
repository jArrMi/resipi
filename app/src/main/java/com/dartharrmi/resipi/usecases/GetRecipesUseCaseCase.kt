package com.dartharrmi.resipi.usecases

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.rxjava2.flowable
import com.dartharrmi.resipi.domain.Recipe
import com.dartharrmi.resipi.repositories.ISpoonacularDataSource.Repository
import com.dartharrmi.resipi.repositories.paging.RecipePagingSource
import io.reactivex.Flowable

interface IGetRecipesUseCase {
    companion object {
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

    fun execute(query: String): Flowable<PagingData<Recipe>>
}

class GetRecipesUseCaseCase(private val repository: Repository) : IGetRecipesUseCase {

    override fun execute(query: String): Flowable<PagingData<Recipe>> =
        Pager(PagingConfig(pageSize = 10)) {
            RecipePagingSource(query, repository)
        }.flowable
}