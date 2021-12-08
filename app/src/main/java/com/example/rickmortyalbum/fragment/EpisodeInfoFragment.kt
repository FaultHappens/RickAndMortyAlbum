package com.example.rickmortyalbum.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rickmortyalbum.adapter.CharactersListAdapter
import com.example.rickmortyalbum.data.CharacterData
import com.example.rickmortyalbum.data.EpisodeData
import com.example.rickmortyalbum.databinding.FragmentEpisodeInfoBinding
import com.example.rickmortyalbum.viewmodel.CharactersViewModel
import io.reactivex.schedulers.Schedulers
import org.koin.android.viewmodel.ext.android.viewModel

class EpisodeInfoFragment : Fragment() {
    private val viewModel: CharactersViewModel by viewModel()
    private val args: EpisodeInfoFragmentArgs by navArgs()
    private lateinit var charactersListAdapter: CharactersListAdapter
    private lateinit var fragmentEpisodeInfoBinding: FragmentEpisodeInfoBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentEpisodeInfoBinding = FragmentEpisodeInfoBinding.inflate(layoutInflater)
        return fragmentEpisodeInfoBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        charactersListAdapter = CharactersListAdapter {
            Navigation.findNavController(view).navigate(
                EpisodeInfoFragmentDirections.actionEpisodeInfoFragmentToCharacterInfoFragment(it)
            )
        }
        fragmentEpisodeInfoBinding.recyclerView.adapter = charactersListAdapter
        fragmentEpisodeInfoBinding.recyclerView.layoutManager =
            LinearLayoutManager(activity?.applicationContext)
        initInfoPage()
    }

    private fun initInfoPage() {
        fragmentEpisodeInfoBinding.episodeNameTV.text = args.episode.name
        fragmentEpisodeInfoBinding.episodeAirDateTV.text = args.episode.air_date
        fragmentEpisodeInfoBinding.episodeTV.text = args.episode.episode
        viewModel.progressLiveData.observe(viewLifecycleOwner, {
            fragmentEpisodeInfoBinding.simpleProgressBar.progress = it
        })


        val list = mutableListOf<CharacterData>()
        for (i in args.episode.characters) {
            val dispose = viewModel.getCharactersWithID(i.substring(CharactersViewModel.CHARACTER_ID_START_INDEX)).subscribeOn(
                Schedulers.io()).observeOn(Schedulers.io()).subscribe({
                list.add(it)
                charactersListAdapter.submitList(list)
                fragmentEpisodeInfoBinding.simpleProgressBar.visibility = View.INVISIBLE
            }, {
                Log.d("LOL", it.toString())
            }, {
                TODO()
            })
            dispose.dispose()
        }

    }
}