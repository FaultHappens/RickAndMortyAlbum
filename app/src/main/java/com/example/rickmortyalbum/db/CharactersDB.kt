package com.example.rickmortyalbum.db

import android.content.Context
import androidx.room.*
import com.example.rickmortyalbum.data.CharacterData
import com.example.rickmortyalbum.data.Converters
import com.google.gson.Gson




@Database(entities = [CharacterData::class], version = 1)
@TypeConverters(Converters::class)
abstract class CharactersDB : RoomDatabase() {
    abstract fun characterDao(): CharactersDAO

    companion object {
        @Volatile
        private var INSTANCE: CharactersDB? = null

        fun getDatabase(context: Context): CharactersDB {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CharactersDB::class.java,
                    "characters_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }

}

