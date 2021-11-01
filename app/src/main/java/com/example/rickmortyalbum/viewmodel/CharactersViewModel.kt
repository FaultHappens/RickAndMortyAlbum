package com.example.rickmortyalbum.viewmodel

import android.util.Log
import android.widget.ProgressBar
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.rickmortyalbum.data.CharacterData
import com.example.rickmortyalbum.data.CharactersPageData
import com.example.rickmortyalbum.db.CharactersDB
import com.example.rickmortyalbum.retriever.DataRetriever
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CharactersViewModel : ViewModel() {
    val charactersData: MutableLiveData<MutableList<CharacterData>> by lazy {
        MutableLiveData<MutableList<CharacterData>>()
    }
    val charactersPageData: MutableLiveData<CharactersPageData> by lazy {
        MutableLiveData<CharactersPageData>()
    }

    private lateinit var resultList: CharactersPageData
    lateinit var progressBar: ProgressBar// Todo იგივე რაც ეპიზოდებში
    val characters = mutableListOf<CharacterData>()

    fun getCharacters(): Flow<PagingData<CharacterData>> {
        return DataRetriever().getCharacters().cachedIn(viewModelScope)
    }

    fun getCharactersWithID(characterUrls: List<String>,  db: CharactersDB) {
        Log.d("LOL", "IMMA HERE")

        viewModelScope.launch(Dispatchers.IO) {
            for (i in characterUrls) {
                //val characterDao = db.characterDao()// Todo იგივე რაც ეპიზოდებში
                val characterID: Int = i.substring(41).toInt()
                //val character: CharacterData = characterDao.getByID(characterID)
                ///Log.d("LOLKEK", character.toString())
                characters.add(DataRetriever().getCharacterWithID(characterID.toString()))
                progressBar.progress += 100 / characterUrls.count()
            }
            withContext(Dispatchers.Main) {
                charactersData.value = characters as ArrayList<CharacterData>
            }
        }
    }

}