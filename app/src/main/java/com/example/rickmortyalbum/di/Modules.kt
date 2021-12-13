package com.example.rickmortyalbum.di

import androidx.room.Room
import com.example.rickmortyalbum.api.API
import com.example.rickmortyalbum.db.CharactersDB
import com.example.rickmortyalbum.db.CharactersDBRepository
import com.example.rickmortyalbum.db.EpisodesDB
import com.example.rickmortyalbum.db.EpisodesDBRepository
import com.example.rickmortyalbum.retriever.DataRetriever
import com.example.rickmortyalbum.viewmodel.CharactersViewModel
import com.example.rickmortyalbum.viewmodel.EpisodesViewModel
import com.google.gson.Gson
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

val module = module{
    single(override = true, qualifier = named("PagingAPI")) { provideAPIForPaging(get(qualifier = named("Paging"))) }
    single(override = true, qualifier = named("RxJavaAPI")) { provideAPIForRxJava(get(qualifier = named("RxJava"))) }
    single(override = true, qualifier = named("Paging")) { provideRetrofitForPager() }
    single(override = true, qualifier = named("RxJava")) { provideRetrofitForRxJava() }
    single { DataRetriever(get(qualifier = named("PagingAPI")), get(qualifier = named("RxJavaAPI")))}
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

fun provideRetrofitForPager(): Retrofit {
    return Retrofit.Builder().baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create()).build()
}

fun provideRetrofitForRxJava(): Retrofit {
    return Retrofit.Builder().baseUrl(baseUrl)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create()).addConverterFactory(GsonConverterFactory.create()).build()
}

fun provideAPIForRxJava(retrofit: Retrofit): API = retrofit.create(API::class.java)

fun provideAPIForPaging(retrofit: Retrofit): API = retrofit.create(API::class.java)

private val baseUrl = "https://rickandmortyapi.com/api/"