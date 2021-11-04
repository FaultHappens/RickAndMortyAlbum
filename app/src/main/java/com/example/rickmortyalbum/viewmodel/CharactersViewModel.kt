package com.example.rickmortyalbum.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.rickmortyalbum.data.CharacterData
import com.example.rickmortyalbum.db.CharactersDB
import com.example.rickmortyalbum.db.CharactersDBRepository
import com.example.rickmortyalbum.retriever.DataRetriever
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CharactersViewModel(application: Application, private val repository: CharactersDBRepository) : AndroidViewModel(application) {

    val charactersData: MutableLiveData<MutableList<CharacterData>> by lazy {
        MutableLiveData<MutableList<CharacterData>>()
    }


    val progressLiveData: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>()
    }

    val characters = mutableListOf<CharacterData>()

    fun getCharacters(): Flow<PagingData<CharacterData>> {
        return DataRetriever().getCharacters().cachedIn(viewModelScope)
    }

    fun getCharactersWithID(characterUrls: List<String>) {
        progressLiveData.value = 0

        viewModelScope.launch(Dispatchers.IO) {
            for (i in characterUrls) {
                val characterID: Int = i.substring(CHARACTER_ID_START_INDEX).toInt()
                var character: CharacterData? = repository.getById(characterID)
                if (character == null) {
                    Log.d("LOL", "Getting character from API")
                    character = DataRetriever().getCharacterWithID(characterID.toString())
                    characters.add(character)
                    repository.insert(character)
                } else {
                    Log.d("LOL", "Getting character from DB")
                    characters.add(character)
                }
                withContext(Dispatchers.Main) {
                    progressLiveData.value =
                        progressLiveData.value?.plus(100 / characterUrls.count())
                }
            }
            withContext(Dispatchers.Main) {
                charactersData.value = characters as ArrayList<CharacterData>
            }
        }
    }


    companion object {
        const val CHARACTER_ID_START_INDEX = 42
    }

}