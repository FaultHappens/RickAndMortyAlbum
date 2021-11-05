package com.example.rickmortyalbum.di

import androidx.room.Room
import com.example.rickmortyalbum.BuildConfig
import com.example.rickmortyalbum.api.API
import com.example.rickmortyalbum.db.CharactersDB
import com.example.rickmortyalbum.db.CharactersDBRepository
import com.example.rickmortyalbum.db.EpisodesDB
import com.example.rickmortyalbum.db.EpisodesDBRepository
import com.example.rickmortyalbum.retriever.DataRetriever
import com.example.rickmortyalbum.viewmodel.CharactersViewModel
import com.example.rickmortyalbum.viewmodel.EpisodesViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val module = module{
    single { provideAPI(get()) }
    single { provideRetrofit() }
    single { DataRetriever(get())}
}

val charactersModule = module{
    factory { Room.databaseBuilder(get(), CharactersDB::class.java, "characters_db").build() }
    factory { get<CharactersDB>().characterDao() }
    factory{ CharactersDBRepository(get()) }
    viewModel { CharactersViewModel(get(), get(), get()) }

}

val episodesModule = module{
    factory { Room.databaseBuilder(get(), EpisodesDB::class.java, "episodes_db").build() }
    factory { get<EpisodesDB>().episodesDao() }
    factory{ EpisodesDBRepository(get()) }
    viewModel { EpisodesViewModel(get(), get(), get()) }
}

fun provideRetrofit(): Retrofit {
    return Retrofit.Builder().baseUrl("https://rickandmortyapi.com/api/")
        .addConverterFactory(GsonConverterFactory.create()).build()
}

fun provideAPI(retrofit: Retrofit): API = retrofit.create(API::class.java)