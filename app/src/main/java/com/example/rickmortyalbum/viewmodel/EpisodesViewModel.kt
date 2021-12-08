package com.example.rickmortyalbum.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.rxjava2.flowable
import com.example.rickmortyalbum.data.EpisodeData
import com.example.rickmortyalbum.db.*
import com.example.rickmortyalbum.retriever.DataRetriever
import io.reactivex.Flowable
import io.reactivex.Observable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.internal.wait
import java.util.*

class EpisodesViewModel(
    application: Application,
    private val repository: EpisodesDBRepository,
    private val dataRetriever: DataRetriever
) : AndroidViewModel(application) {

    val episodesData: MutableLiveData<MutableList<EpisodeData>> by lazy {
        MutableLiveData<MutableList<EpisodeData>>()
    }

    val progressLiveData: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>()
    }


    fun getEpisodes(): Flowable<PagingData<EpisodeData>> {
        return dataRetriever.getEpisodes().flowable
    }

    fun getEpisodesData(episodeUrls: List<String>): MutableList<EpisodeData>{
        val episodes = mutableListOf<EpisodeData>()
        for (i in episodeUrls) {
            val episodeID: Int = i.substring(EPISODE_ID_START_INDEX).toInt()
//                var episode: EpisodeData? = repository.getById(episodeID)
            var episode: EpisodeData? = null
            if (episode == null) {
                Log.d("LOL", "Getting episode from API")
                dataRetriever.getEpisodeWithId(episodeID.toString()) { response ->
                    Log.d("LOL", response.toString())
                    episode = response
                    episode?.let { episodes.add(it) }
                }
//                    episode?.let { repository.insert(it) }
            } else {
                Log.d("LOL", "Getting episode from DB")
                episodes.add(episode!!)
            }
        }
        return episodes
    }

    fun getEpisodesDataWithID(episodeUrls: List<String>): Observable<MutableList<EpisodeData>>? {


        return Observable.just(getEpisodesData(episodeUrls))

    }

    companion object {
        const val EPISODE_ID_START_INDEX = 40
    }
}