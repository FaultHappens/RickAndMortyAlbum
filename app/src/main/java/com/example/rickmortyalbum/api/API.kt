package com.example.rickmortyalbum.api

import com.example.rickmortyalbum.data.CharacterData
import com.example.rickmortyalbum.data.CharactersPageData
import com.example.rickmortyalbum.data.EpisodeData
import com.example.rickmortyalbum.data.EpisodesPageData
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface API {
    @GET("episode")
    suspend fun getEpisodesListAPI(@Query("page") page: Int): Response<EpisodesPageData>

    @GET("character")
    suspend fun getCharactersAPI(@Query("page") page: Int): Response<CharactersPageData>

    @GET
    fun getCharacterAPI(@Url url: String): Call<CharacterData>

    @GET
    fun getEpisodeAPI(@Url url: String): Call<EpisodeData>
}