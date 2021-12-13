package com.example.rickmortyalbum.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.rxjava2.flowable
import com.example.rickmortyalbum.data.CharacterData
import com.example.rickmortyalbum.data.EpisodeData
import com.example.rickmortyalbum.db.CharactersDBRepository
import com.example.rickmortyalbum.retriever.DataRetriever
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.Observable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CharactersViewModel(application: Application,
                          private val repository: CharactersDBRepository,
                          private val dataRetriever: DataRetriever) : AndroidViewModel(application) {

    val charactersData: MutableLiveData<MutableList<CharacterData>> by lazy {
        MutableLiveData<MutableList<CharacterData>>()
    }


    val progressLiveData: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>()
    }

    val characters = mutableListOf<CharacterData>()

//    fun getCharacters(): Flow<PagingData<CharacterData>> {
//        return dataRetriever.getCharacters().cachedIn(viewModelScope)
//    }

    fun getCharacters(): Flowable<PagingData<CharacterData>> {
        return dataRetriever.getCharacters().flowable
    }

    fun getCharactersWithID(id: String): Observable<CharacterData> {
        Log.d("LOL", "Requeting info on character with id '$id'")
        return dataRetriever.getCharacterWithID(id)
    }


    companion object {
        const val CHARACTER_ID_START_INDEX = 42
    }

}