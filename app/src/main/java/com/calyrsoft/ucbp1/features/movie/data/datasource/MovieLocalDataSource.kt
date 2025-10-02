package com.calyrsoft.ucbp1.features.movie.data.datasource


import com.calyrsoft.ucbp1.features.movie.data.database.dao.IMovieDao
import com.calyrsoft.ucbp1.features.movie.data.database.entity.MovieEntity

class MovieLocalDataSource ( val dao: IMovieDao
    ) {
        suspend fun rate(movieId: Int, rate: Int) {
            dao.insert(MovieEntity(movieId, rate))
        }

        suspend fun getRatingForMovie(movieId: Int): Int {
            return dao.getRatingForMovie(movieId) ?: 0
        }
    }