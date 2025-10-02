package com.calyrsoft.ucbp1.features.movie.data

import com.calyrsoft.ucbp1.features.movie.data.database.entity.MovieEntity
import com.calyrsoft.ucbp1.features.movie.domain.model.MovieModel


fun MovieModel.toEntity(rate: Int) : MovieEntity {
    return MovieEntity(
        id = id,
        rate = rate
    )
}
