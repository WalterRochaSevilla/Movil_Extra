package com.calyrsoft.ucbp1.features.movie.data

import com.calyrsoft.ucbp1.features.github.domain.model.UrlPath
import com.calyrsoft.ucbp1.features.movie.data.api.dto.MovieDto
import com.calyrsoft.ucbp1.features.movie.data.database.entity.MovieEntity
import com.calyrsoft.ucbp1.features.movie.domain.model.MovieModel


fun MovieModel.toEntity(rate: Int) : MovieEntity {
    return MovieEntity(
        id = id,
        rate = rate
    )
}

fun MovieDto.toModel(): MovieModel {
    return MovieModel(
        id = id,
        pathUrl = UrlPath("https://image.tmdb.org/t/p/w185" + pathUrl),
        title = title,
        overview = overview
    )
}