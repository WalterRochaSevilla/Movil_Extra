package com.calyrsoft.ucbp1.features.dollar.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.calyrsoft.ucbp1.features.dollar.data.database.dao.IDollarDao
import com.calyrsoft.ucbp1.features.dollar.data.database.entity.DollarEntity
import com.calyrsoft.ucbp1.features.movie.data.database.dao.IMovieDao
import com.calyrsoft.ucbp1.features.movie.data.database.entity.MovieEntity

val Migration_1_2 =object:Migration(1,2){
    override fun migrate(database: SupportSQLiteDatabase){
        database.execSQL("ALTER TABLE 'movies' ADD COLUMN title TEXT")
        database.execSQL("UPDATE 'movies' SET title = '' WHERE title is NULL")
    }
}

@Database(entities = [DollarEntity::class, MovieEntity::class], version = 2)
abstract class AppRoomDatabase : RoomDatabase() {
    abstract fun dollarDao(): IDollarDao
    abstract fun movieDao(): IMovieDao


    companion object {
        @Volatile
        private var Instance: AppRoomDatabase? = null


        fun getDatabase(context: Context): AppRoomDatabase {
            // if the Instance is not null, return it, otherwise create a new database instance.
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, AppRoomDatabase::class.java, "dollar_db")
                    .addMigrations(Migration_1_2)
                    .build()
                    .also { Instance = it }
            }
        }
    }
}
