package com.example.rickmortyalbum.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.rickmortyalbum.data.Converters
import com.example.rickmortyalbum.data.EpisodeData

@Database(entities = [EpisodeData::class], version = 1)
@TypeConverters(Converters::class)
abstract class EpisodesDB : RoomDatabase() {
    abstract fun episodesDao(): EpisodesDAO

    companion object {
        @Volatile
        private var INSTANCE: EpisodesDB? = null

        fun getDatabase(context: Context): EpisodesDB {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    EpisodesDB::class.java,
                    "episodes_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }

}