package com.calyrsoft.ucbp1.features.movie.data.repository

import com.calyrsoft.ucbp1.features.movie.data.datasource.MovieLocalDataSource
import com.calyrsoft.ucbp1.features.movie.data.datasource.MovieRemoteDataSource
import com.calyrsoft.ucbp1.features.movie.domain.model.MovieModel
import com.calyrsoft.ucbp1.features.movie.domain.repository.IMoviesRepository

class MovieRepository(
    private val movieRemoteDataSource: MovieRemoteDataSource,
    private val movieLocalDataSource: MovieLocalDataSource
): IMoviesRepository {
    override suspend fun fetchPopularMovies(): Result<List<MovieModel>> {
       return try {
          val remoteMovies = movieRemoteDataSource.fetchPopularMovies()
          remoteMovies.fold(
              onSuccess = { movies ->
                  val rateMovies = movies.map { movie ->
                      val rating = movieLocalDataSource.getRatingForMovie(movie.id)
                      movie.copy(  rating = rating)
                  }
                      .sortedByDescending { it.rating }
                  Result.success(rateMovies)
              },
              onFailure = {
                Result.failure(it)
              }
          )


       } catch (e: Exception) {
           Result.failure(e)
       }
    }

    override suspend fun rateMovie(movieId: Int, rating: Int): Result<Unit> {
        movieLocalDataSource.rate(movieId, rating)
        return Result.success(Unit)

    }

}