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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EpisodesViewModel(application: Application,
                        private val repository: EpisodesDBRepository,
                        private val dataRetriever: DataRetriever) : AndroidViewModel(application) {

    val episodesData: MutableLiveData<MutableList<EpisodeData>> by lazy {
        MutableLiveData<MutableList<EpisodeData>>()
    }

    val progressLiveData: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>()
    }


    fun getEpisodes(): Flowable<PagingData<EpisodeData>> {
        return dataRetriever.getEpisodes().flowable
    }


    fun getEpisodesDataWithID(episodeUrls: List<String>) {
        progressLiveData.value = 0

        viewModelScope.launch(Dispatchers.IO) {
            val episodes = mutableListOf<EpisodeData>()
            for (i in episodeUrls) {
                val episodeID: Int = i.substring(EPISODE_ID_START_INDEX).toInt()
                var episode: EpisodeData? = repository.getById(episodeID)

                if (episode == null) {
                    Log.d("LOL", "Getting episode from API")
                    episode = dataRetriever.getEpisodeWithId(episodeID.toString())
                    episodes.add(episode)
                    repository.insert(episode)
                } else {
                    Log.d("LOL", "Getting episode from DB")
                    episodes.add(episode)
                }

                withContext(Dispatchers.Main) {
                    progressLiveData.value = progressLiveData.value?.plus(100 / episodeUrls.count())
                }
            }
            withContext(Dispatchers.Main) {
                episodesData.value = episodes as ArrayList<EpisodeData>
            }
        }
    }

    companion object {
        const val EPISODE_ID_START_INDEX = 40
    }
}