package com.example.rickmortyalbum.retriever

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.rxjava2.flowable
import com.example.rickmortyalbum.api.API
import com.example.rickmortyalbum.data.CharacterData
import com.example.rickmortyalbum.data.CharactersPagingSource
import com.example.rickmortyalbum.data.EpisodeData
import com.example.rickmortyalbum.data.EpisodesPagingSource
import com.example.rickmortyalbum.fragment.CharacterInfoFragmentArgs
import io.reactivex.Observable
import kotlinx.coroutines.flow.Flow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class DataRetriever(private val service: API) {
    fun getEpisodes(): Pager<Int, EpisodeData>{
        return Pager(
            config = PagingConfig(
                pageSize = 30,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                EpisodesPagingSource(service = service)
            }
        )
    }

    fun getCharacters(): Pager<Int, CharacterData>{
        return Pager(
            config = PagingConfig(
                pageSize = 30,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                CharactersPagingSource(service = service)
            }
        )
    }

    fun getCharacterWithID(id: String, responseCallback: (CharacterData?) -> Unit){
        service.getCharacterAPI("character/$id").enqueue(object: Callback<CharacterData>{
            override fun onResponse(call: Call<CharacterData>, response: Response<CharacterData>) {
                responseCallback.invoke(response.body())
            }

            override fun onFailure(call: Call<CharacterData>, t: Throwable) {
                responseCallback.invoke(null)
            }

        })}

    fun getEpisodeWithId(id: String, responseCallback: (EpisodeData?) -> Unit){
        service.getEpisodeAPI("episode/$id").enqueue(object: Callback<EpisodeData>{
            override fun onResponse(call: Call<EpisodeData>, response: Response<EpisodeData>) {
                responseCallback.invoke(response.body())
            }

            override fun onFailure(call: Call<EpisodeData>, t: Throwable) {
                responseCallback.invoke(null)
            }

        })}
}