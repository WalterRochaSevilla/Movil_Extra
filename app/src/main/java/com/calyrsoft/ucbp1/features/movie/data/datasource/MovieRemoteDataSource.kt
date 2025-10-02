package com.calyrsoft.ucbp1.features.movie.data.datasource

import com.calyrsoft.ucbp1.features.github.domain.model.UrlPath
import com.calyrsoft.ucbp1.features.movie.data.api.MovieService
import com.calyrsoft.ucbp1.features.movie.domain.model.MovieModel

class MovieRemoteDataSource(
    private val movieServie: MovieService,
    private val apiKey: String
) {
    suspend fun fetchPopularMovies(): Result<List<MovieModel>> {
        val response = movieServie.fetchPopularMovies(apiKey = apiKey)
        return if (response.isSuccessful) {
            val moviePage = response.body()
            if (moviePage != null) {
                return Result.success(moviePage.results.map { dto ->  MovieModel(dto.id,UrlPath("https://image.tmdb.org/t/p/w185"+dto.pathUrl), dto.title) } )
            }
            Result.success(emptyList())
        } else {
            Result.failure(Exception("Error"))
        }
    }
}