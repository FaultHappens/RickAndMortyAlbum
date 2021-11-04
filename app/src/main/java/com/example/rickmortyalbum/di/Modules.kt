package com.example.rickmortyalbum.di

import androidx.room.Room
import com.example.rickmortyalbum.db.CharactersDB
import com.example.rickmortyalbum.db.CharactersDBRepository
import com.example.rickmortyalbum.db.EpisodesDB
import com.example.rickmortyalbum.db.EpisodesDBRepository
import com.example.rickmortyalbum.viewmodel.CharactersViewModel
import com.example.rickmortyalbum.viewmodel.EpisodesViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val modules = module {
    factory { Room.databaseBuilder(get(), CharactersDB::class.java, "characters_db").build() }
    factory { get<CharactersDB>().characterDao() }
    factory{ CharactersDBRepository(get()) }
    viewModel { CharactersViewModel(get(), get()) }

    factory { Room.databaseBuilder(get(), EpisodesDB::class.java, "episodes_db").build() }
    factory { get<EpisodesDB>().episodesDao() }
    factory{ EpisodesDBRepository(get()) }
    viewModel { EpisodesViewModel(get(), get()) }
}