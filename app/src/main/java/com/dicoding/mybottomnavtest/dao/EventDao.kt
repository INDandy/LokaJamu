package com.dicoding.mybottomnavtest.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dicoding.mybottomnavtest.NewsResponse.ArticlesItem


@Dao
interface EventDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(event: ArticlesItem)

    @Query("DELETE FROM events WHERE id = :eventId")
    suspend fun delete(eventId: Int)

    @Query("SELECT COUNT(*) FROM events WHERE id = :eventId")
    suspend fun isFavorite(eventId: Int): Int

    @Query("SELECT * FROM events WHERE isFavorite = 1")
    fun getAllFavorites(): LiveData<List<ArticlesItem>>
}




