package com.dartharrmi.resipi.repositories.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.LoadType.*
import androidx.paging.PagingState
import androidx.paging.RemoteMediator.MediatorResult.Success
import androidx.paging.rxjava2.RxRemoteMediator
import androidx.room.RoomDatabase
import com.dartharrmi.resipi.BuildConfig
import com.dartharrmi.resipi.domain.Recipe
import com.dartharrmi.resipi.domain.RemoteKey
import com.dartharrmi.resipi.domain.exceptions.RecipesNotfoundException
import com.dartharrmi.resipi.repositories.ISpoonacularDataSource.Repository
import com.dartharrmi.resipi.repositories.dao.RecipeDao
import com.dartharrmi.resipi.repositories.dao.RemoteKeyDao
import com.dartharrmi.resipi.usecases.IGetRecipesUseCase
import com.dartharrmi.resipi.utils.applyIoMain
import com.dartharrmi.resipi.webservice.dto.response.GetRecipeIngredientsDTO
import com.dartharrmi.resipi.webservice.dto.response.toDomain
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException
import java.io.IOException

@ExperimentalPagingApi
class RecipesRemoteMediator(private val query: String,
                            private val repository: Repository,
                            private val database: RoomDatabase,
                            private val recipeDao: RecipeDao, private val
                            remoteKeyDao: RemoteKeyDao):
        RxRemoteMediator<Int, Recipe>() {

    private val numberOfRecipes = 4
    private var nextKey = 0

    override fun loadSingle(loadType: LoadType, state: PagingState<Int, Recipe>): Single<MediatorResult> {
        // The network load method takes an optional String parameter. For every page
        // after the first, pass the String token returned from the previous page to
        // let it continue from where it left off. For REFRESH, pass null to load the
        // first page.
        // The network load method takes an optional String parameter. For every page
        // after the first, pass the String token returned from the previous page to
        // let it continue from where it left off. For REFRESH, pass null to load the
        // first page.
        var remoteKeySingle: Single<RemoteKey>? = null
        when (loadType) {
            REFRESH ->
                // Initial load should use null as the page key, so you can return null
                // directly.
                remoteKeySingle = Single.just(RemoteKey(query, null))
            PREPEND ->
                // In this example, you never need to prepend, since REFRESH will always
                // load the first page in the list. Immediately return, reporting end of
                // pagination.
                return Single.just(Success(true))
            APPEND -> remoteKeySingle = remoteKeyDao.remoteKeyByQuery(query) // Query remoteKeyDao for the next RemoteKey.
        }

        return remoteKeySingle
                .applyIoMain()
                .flatMap { t: RemoteKey ->
                    if (loadType != REFRESH && t.nextKey == null) {
                        Single.just(Success(true))
                    }

                    val currentKey = t.nextKey ?: 0
                    nextKey = currentKey + numberOfRecipes
                    repository
                            .searchRecipes(BuildConfig.SPOONACULAR_API_KEY, query, currentKey, 10)
                            .flatMapObservable {
                                if (it.results.isEmpty()) {
                                    throw RecipesNotfoundException(query)
                                }

                                Observable.fromIterable(it.results)
                            }
                            .flatMapSingle { recipe: Recipe ->
                                repository
                                        .getRecipeDetails(recipe.id, BuildConfig.SPOONACULAR_API_KEY)
                                        .map { t: GetRecipeIngredientsDTO -> t.toDomain() }
                                        .map { ingredientsResponse ->
                                            ingredientsResponse.ingredients.forEach {
                                                it.image = IGetRecipesUseCase.getIngredientUrl(
                                                        IGetRecipesUseCase.SIZE_100_X_100,
                                                        it.image
                                                )
                                            }
                                            recipe.apply {
                                                image = IGetRecipesUseCase.getRecipeUrl(
                                                        this.id,
                                                        IGetRecipesUseCase.SIZE_636_X_393
                                                )
                                                ingredients = ingredientsResponse.ingredients
                                            }
                                        }
                            }
                            .toList()
                            .subscribeOn(Schedulers.io())
                            .map {
                                database.runInTransaction {
                                    if (loadType == LoadType.REFRESH) {
                                        recipeDao.deleteByQuery(query)
                                        remoteKeyDao.deleteByQuery(query)
                                    }

                                    // Update RemoteKey for this query.
                                    remoteKeyDao.insertOrReplace(RemoteKey(query, nextKey))

                                    // Insert new users into database, which invalidates the current
                                    // PagingData, allowing Paging to present the updates in the DB.
                                    recipeDao.insertAll(it);
                                }

                                return@map Success(true)
                            }.doOnError {
                                if (it is IOException || it is HttpException) {

                                    Single.just<MediatorResult.Error>(MediatorResult.Error(it))
                                } else Single.error(it)
                            }
                }
    }
}