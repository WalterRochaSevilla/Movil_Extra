package com.calyrsoft.ucbp1.features.movie.domain.repository

import com.calyrsoft.ucbp1.features.movie.domain.model.MovieModel

interface IMoviesRepository {
    suspend fun fetchPopularMovies(): Result<List<MovieModel>>
    suspend fun rateMovie(movieId: Int, rating: Int): Result<Unit>
}