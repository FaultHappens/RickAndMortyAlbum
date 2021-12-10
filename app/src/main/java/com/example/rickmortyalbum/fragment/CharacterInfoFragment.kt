package com.example.rickmortyalbum.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.rickmortyalbum.adapter.EpisodesListAdapter
import com.example.rickmortyalbum.data.EpisodeData
import com.example.rickmortyalbum.databinding.FragmentCharacterInfoBinding
import com.example.rickmortyalbum.extensions.setImageFromUrl
import com.example.rickmortyalbum.viewmodel.CharactersViewModel.Companion.CHARACTER_ID_START_INDEX
import com.example.rickmortyalbum.viewmodel.EpisodesViewModel
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.koin.android.viewmodel.ext.android.viewModel
import java.util.*

class CharacterInfoFragment : Fragment() {

    private val viewModel: EpisodesViewModel by viewModel()
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
        episodesListAdapter = EpisodesListAdapter { item ->
            navigate(view, item)
        }
        fragmentCharacterInfoBinding.recyclerView.layoutManager =
            LinearLayoutManager(activity?.applicationContext)
        fragmentCharacterInfoBinding.recyclerView.adapter = episodesListAdapter
        initInfoPage()
    }

    private fun initInfoPage() {
        with(fragmentCharacterInfoBinding) {
            characterGenderTV.text = args.character.gender
            characterNameTV.text = args.character.name
            characterSpeciesTV.text = args.character.species
            characterStatusTV.text = args.character.status
            characterImageIV.setImageFromUrl(args.character.image)
            viewModel.episodesData.observe(viewLifecycleOwner, {
                episodesListAdapter.submitList(it)
                simpleProgressBar.visibility = View.INVISIBLE
            })
            viewModel.progressLiveData.observe(viewLifecycleOwner, {
                simpleProgressBar.progress = it
            })
        }



        val list = mutableListOf<EpisodeData>()
        for (i in args.character.episode) {
            val dispose = viewModel.getEpisodesDataWithID(i.substring(EPISODE_ID_START_INDEX))
                .subscribeOn(Schedulers.io()).observeOn(
                Schedulers.io()
            ).subscribe(object : Observer<EpisodeData> {
                override fun onSubscribe(d: Disposable) {

                }

                override fun onNext(t: EpisodeData) {
                    list.add(t)
                    episodesListAdapter.submitList(list)
                    fragmentCharacterInfoBinding.simpleProgressBar.visibility = View.INVISIBLE
                }

                override fun onError(e: Throwable) {
                    Log.d("LOL", e.toString())
                }

                override fun onComplete() {

                }

            })

        }


    }

    private fun navigate(view: View, item: EpisodeData){
        Navigation.findNavController(view).navigate(
            CharacterInfoFragmentDirections.actionCharacterInfoFragmentToEpisodeInfoFragment(
                item
            )
        )
    }


    companion object {
        private const val EPISODE_ID_START_INDEX = 40
    }
}