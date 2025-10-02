package com.calyrsoft.ucbp1.features.movie.domain.usecase

import com.calyrsoft.ucbp1.features.movie.domain.repository.IMoviesRepository

class RateMovieUseCase(private val movieRepository: IMoviesRepository) {

    suspend fun invoke(movieId: Int, rating: Int): Result<Unit> {
        return movieRepository.rateMovie(movieId, rating)
    }
}