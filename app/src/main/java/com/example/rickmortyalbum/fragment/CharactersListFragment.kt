package com.example.rickmortyalbum.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rickmortyalbum.adapter.CharactersListAdapter
import com.example.rickmortyalbum.databinding.FragmentCharactersListBinding
import com.example.rickmortyalbum.viewmodel.CharactersViewModel

class CharactersListFragment : Fragment() {

    private lateinit var fragmentCharactersListBinding: FragmentCharactersListBinding
    private lateinit var charactersListAdapter: CharactersListAdapter
    private val charactersViewModel: CharactersViewModel by viewModels()
    private var pagesLoaded: Int = 1
    private var pagesTotal: Int = 0




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentCharactersListBinding = FragmentCharactersListBinding.inflate(layoutInflater)
        return fragmentCharactersListBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        charactersListAdapter = CharactersListAdapter(){
            Navigation.findNavController(view).navigate(CharactersListFragmentDirections.actionCharactersListFragmentToCharacterInfoFragment(it))
        }
        fragmentCharactersListBinding.charactersRecyclerView.layoutManager =
            LinearLayoutManager(activity?.applicationContext)
        fragmentCharactersListBinding.charactersRecyclerView.adapter = charactersListAdapter

        charactersViewModel.getCharacters().observe(viewLifecycleOwner, {
            charactersListAdapter.submitList(it.characters)
            pagesTotal = it.info.pages
        })
        fragmentCharactersListBinding.button.setOnClickListener{
            if(pagesLoaded != pagesTotal){
                pagesLoaded++
                charactersViewModel.retrieveCharacters(pagesLoaded)
                if(pagesLoaded == pagesTotal){
                    fragmentCharactersListBinding.button.isEnabled = false
                }
            }
        }


    }
}