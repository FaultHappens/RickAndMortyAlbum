package com.example.rickmortyalbum.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.rxjava2.flowable
import com.example.rickmortyalbum.data.CharacterData
import com.example.rickmortyalbum.db.CharactersDBRepository
import com.example.rickmortyalbum.retriever.DataRetriever
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.internal.util.HalfSerializer.onNext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CharactersViewModel(
    application: Application,
    private val repository: CharactersDBRepository,
    private val dataRetriever: DataRetriever
) : AndroidViewModel(application) {


    val progressLiveData: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>()
    }



    fun getCharacters(): Flowable<PagingData<CharacterData>> {
        return dataRetriever.getCharacters().flowable
    }

    fun getCharactersWithID(characterUrls: List<String>): Observable<MutableList<CharacterData>>{
        progressLiveData.value = 0
        val characters = mutableListOf<CharacterData>()
        for (i in characterUrls) {
            val characterID: Int = i.substring(CHARACTER_ID_START_INDEX).toInt()
            //var character: CharacterData? = repository.getById(characterID)
            var character: CharacterData? = null
            if (character == null) {
                Log.d("LOL", "Getting character from API")
                dataRetriever.getCharacterWithID(characterID.toString()) { response ->
                    character = response
                }
                character?.let { repository.insert(it) }
                character?.let { characters.add(it) }

            } else {
                Log.d("LOL", "Getting character from DB")
                characters.add(character!!)
            }
            progressLiveData.value =
                progressLiveData.value?.plus(100 / characterUrls.count())

        }
        return Observable.just(characters)
    }


    companion object {
        const val CHARACTER_ID_START_INDEX = 42
    }

}