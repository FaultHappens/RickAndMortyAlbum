package com.example.rickmortyalbum.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.rickmortyalbum.adapter.EpisodesListAdapter
import com.example.rickmortyalbum.databinding.FragmentCharacterInfoBinding
import com.example.rickmortyalbum.viewmodel.EpisodesViewModel

class CharacterInfoFragment : Fragment() {

    private val viewModel: EpisodesViewModel by viewModels()
    private val args: CharacterInfoFragmentArgs by navArgs()
    private lateinit var fragmentCharacterInfoBinding: FragmentCharacterInfoBinding
    private lateinit var episodesListAdapter: EpisodesListAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentCharacterInfoBinding = FragmentCharacterInfoBinding.inflate(layoutInflater)
        return fragmentCharacterInfoBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        episodesListAdapter = EpisodesListAdapter{item->
            Navigation.findNavController(view).navigate(CharacterInfoFragmentDirections.actionCharacterInfoFragmentToEpisodeInfoFragment(
                item
            ))
        }
        fragmentCharacterInfoBinding.recyclerView.layoutManager = LinearLayoutManager(activity?.applicationContext)
        fragmentCharacterInfoBinding.recyclerView.adapter = episodesListAdapter
        initInfoPage()
    }

    private fun initInfoPage() {
        fragmentCharacterInfoBinding.characterGenderTV.text = args.character.gender
        fragmentCharacterInfoBinding.characterNameTV.text = args.character.name
        fragmentCharacterInfoBinding.characterSpeciesTV.text = args.character.species
        fragmentCharacterInfoBinding.characterStatusTV.text = args.character.status
        viewModel.episodesData.observe(viewLifecycleOwner, {
            episodesListAdapter.submitList(it)
            fragmentCharacterInfoBinding.simpleProgressBar.visibility = View.INVISIBLE
        })
        viewModel.progressLiveData.observe(viewLifecycleOwner, {
            fragmentCharacterInfoBinding.simpleProgressBar.progress = it
        })

        context?.let {
            Glide.with(it)
                .load(args.character.image)
                .into(fragmentCharacterInfoBinding.characterImageIV)
        }
        viewModel.getEpisodesDataWithID(args.character.episode)

    }
}