package com.example.rickmortyalbum.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rickmortyalbum.adapter.CharactersPagingListAdapter
import com.example.rickmortyalbum.databinding.FragmentCharactersListBinding
import com.example.rickmortyalbum.viewmodel.CharactersViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class CharactersListFragment : Fragment() {

    private lateinit var fragmentCharactersListBinding: FragmentCharactersListBinding
    private lateinit var charactersPagingListAdapter: CharactersPagingListAdapter
    private val charactersViewModel: CharactersViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentCharactersListBinding = FragmentCharactersListBinding.inflate(layoutInflater)
        return fragmentCharactersListBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        charactersPagingListAdapter = CharactersPagingListAdapter {
            Navigation.findNavController(view).navigate(
                CharactersListFragmentDirections.actionCharactersListFragmentToCharacterInfoFragment(
                    it
                )
            )
        }
        fragmentCharactersListBinding.charactersRecyclerView.layoutManager =
            LinearLayoutManager(activity?.applicationContext)
        fragmentCharactersListBinding.charactersRecyclerView.adapter = charactersPagingListAdapter
        viewLifecycleOwner.lifecycleScope.launch {
            charactersViewModel.getCharacters().collectLatest { characters ->
                Log.d("LOL", characters.toString())
                charactersPagingListAdapter.submitData(characters)
            }
        }
    }
}