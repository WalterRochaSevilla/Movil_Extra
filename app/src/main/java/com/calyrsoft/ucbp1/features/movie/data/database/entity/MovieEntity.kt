package com.calyrsoft.ucbp1.features.movie.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies")
data class MovieEntity(
    @PrimaryKey()
    @ColumnInfo(name = "id")
    var id: Int = 0,

    @ColumnInfo(name = "rate")
    var rate: Int = 0)