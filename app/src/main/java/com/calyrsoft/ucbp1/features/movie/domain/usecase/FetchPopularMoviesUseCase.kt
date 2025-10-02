package com.calyrsoft.ucbp1.features.movie.domain.usecase

import com.calyrsoft.ucbp1.features.movie.domain.model.MovieModel
import com.calyrsoft.ucbp1.features.movie.domain.repository.IMoviesRepository

class FetchPopularMoviesUseCase(
    private val movieRepository: IMoviesRepository
) {
    suspend fun invoke(): Result<List<MovieModel>> {
        return movieRepository.fetchPopularMovies()
    }
}