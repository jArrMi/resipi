package com.dartharrmi.resipi.repositories.paging

import androidx.paging.rxjava2.RxPagingSource
import com.dartharrmi.resipi.BuildConfig
import com.dartharrmi.resipi.domain.Recipe
import com.dartharrmi.resipi.domain.exceptions.RecipesNotfoundException
import com.dartharrmi.resipi.repositories.ISpoonacularDataSource.Repository
import com.dartharrmi.resipi.utils.ResipiLog
import com.dartharrmi.resipi.utils.Utils.SIZE_100_X_100
import com.dartharrmi.resipi.utils.Utils.SIZE_636_X_393
import com.dartharrmi.resipi.utils.Utils.getIngredientUrl
import com.dartharrmi.resipi.utils.Utils.getRecipeUrl
import com.dartharrmi.resipi.utils.applyIoMain
import com.dartharrmi.resipi.webservice.dto.response.GetRecipeIngredientsDTO
import com.dartharrmi.resipi.webservice.dto.response.toDomain
import io.reactivex.Observable
import io.reactivex.Single

/**
 * Incremental data loader for page-keyed content, where requests return keys for next/previous pages.
 *
 */
class RecipePagingSource(private val query: String, private val repository: Repository):
        RxPagingSource<Int, Recipe>() {

    companion object {
        val tag = ResipiLog.makeLogTag(RecipePagingSource::class.java)
    }

    private val numberOfRecipes = 4
    private var offset = 0

    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, Recipe>> {
        return repository
                .searchRecipes(BuildConfig.SPOONACULAR_API_KEY, query, offset, numberOfRecipes)
                .applyIoMain()
                .flatMapObservable {
                    if (it.results.isEmpty()) {
                        throw RecipesNotfoundException(query)
                    }

                    Observable.fromIterable(it.results)
                }
                .flatMapSingle { recipe: Recipe ->
                    repository
                            .getRecipeDetails(recipe.id, BuildConfig.SPOONACULAR_API_KEY)
                            .applyIoMain()
                            .map { t: GetRecipeIngredientsDTO -> t.toDomain() }
                            .map { ingredientsResponse ->
                                ingredientsResponse.ingredients.forEach {
                                    it.image = getIngredientUrl(SIZE_100_X_100, it.image)
                                }
                                recipe.apply {
                                    image = getRecipeUrl(this.id, SIZE_636_X_393)
                                    ingredients = ingredientsResponse.ingredients
                                }
                            }
                }
                .toList()
                .map { t: MutableList<Recipe> -> toLoadResult(t) }
                .doOnError { t -> ResipiLog.LOGE(tag, "An error happened retrieving the recipes", t) }
                .onErrorReturn { t: Throwable -> LoadResult.Error(t) }
    }

    private fun toLoadResult(recipes: List<Recipe>): LoadResult<Int, Recipe> {
        offset += numberOfRecipes
        return LoadResult.Page(recipes, null, offset)
    }
}