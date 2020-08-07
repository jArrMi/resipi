package com.dartharrmi.resipi.repositories.db.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dartharrmi.resipi.domain.Recipe
import io.reactivex.Completable
import io.reactivex.Maybe

@Dao
interface RecipeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(recipes: List<Recipe>): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSingle(recipes: Recipe): Completable

    @Query("SELECT * FROM recipes WHERE title LIKE :query")
    fun pagingSource(query: String): PagingSource<Int, Recipe>

    @Query("DELETE FROM recipes WHERE title LIKE :query")
    fun deleteByQuery(query: String): Completable

    @Query("DELETE FROM recipes")
    fun clearAll(): Completable
}
