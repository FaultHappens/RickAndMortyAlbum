package com.example.rickmortyalbum.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.rickmortyalbum.data.CharacterData

@Database(entities = [CharacterData::class], version = 1)
abstract class CharactersDB : RoomDatabase() {
    abstract fun characterDao(): CharactersDAO
}