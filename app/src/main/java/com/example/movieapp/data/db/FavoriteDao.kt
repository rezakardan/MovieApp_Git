package com.example.movieapp.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow


@Dao
interface FavoriteDao {


    @Insert
    suspend fun addToFavorite(favoriteEntity: FavoriteEntity)


    @Delete
    suspend fun deleteFromFavorite(favoriteEntity: FavoriteEntity)



    @Query("SELECT * FROM FAVORITETABLE ")
    fun getAllFavorites():Flow<List<FavoriteEntity>>



    @Query("SELECT EXISTS (SELECT 1 FROM FAVORITETABLE WHERE id= :id)")
    fun isInDatabaseOrNo(id:String):Flow<Boolean>




}