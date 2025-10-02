package com.calyrsoft.ucbp1.features.movie.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.calyrsoft.ucbp1.features.movie.data.database.entity.MovieEntity

@Dao
interface IMovieDao {
    @Query("SELECT * FROM movies")
    suspend fun getList(): List<MovieEntity>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movie: MovieEntity)


    @Query("DELETE FROM movies")
    suspend fun deleteAll()

    @Query("SELECT rate FROM movies WHERE id = :movieId LIMIT 1")
    suspend fun getRatingForMovie(movieId: Int): Int?
}