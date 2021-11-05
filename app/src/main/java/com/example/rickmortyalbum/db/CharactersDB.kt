package com.example.rickmortyalbum.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.rickmortyalbum.data.CharacterData
import com.example.rickmortyalbum.data.Converters


@Database(entities = [CharacterData::class], version = 1)
@TypeConverters(Converters::class)
abstract class CharactersDB : RoomDatabase() {
    abstract fun characterDao(): CharactersDAO

    companion object {
        @Volatile
        private var INSTANCE: CharactersDB? = null


    }

}

