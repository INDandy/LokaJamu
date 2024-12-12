package com.dicoding.mybottomnavtest.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ArticleDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(article: Article)

    @Query("DELETE FROM articles WHERE id = :articleId")
    suspend fun delete(articleId: Int)

    @Query("SELECT COUNT(*) FROM articles WHERE id = :articleId")
    suspend fun isFavorite(articleId: Int): Int

    @Query("SELECT * FROM articles WHERE isFavorite = 1")
    fun getAllFavorites(): LiveData<List<Article>>

    @Query("SELECT * FROM articles")
    fun getAllArticles(): LiveData<List<Article>>
}


