package com.example.rickmortyalbum.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rickmortyalbum.adapter.EpisodesListAdapter
import com.example.rickmortyalbum.databinding.FragmentEpisodeListBinding
import com.example.rickmortyalbum.viewmodel.EpisodesViewModel

class EpisodeListFragment : Fragment() {

    private lateinit var fragmentEpisodeListBinding: FragmentEpisodeListBinding
    private lateinit var episodesListAdapter: EpisodesListAdapter
    private val episodesViewModel: EpisodesViewModel by viewModels()
    private var pagesLoaded: Int = 1
    private var pagesTotal: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentEpisodeListBinding = FragmentEpisodeListBinding.inflate(layoutInflater)
        return fragmentEpisodeListBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        episodesListAdapter = EpisodesListAdapter(){item->
            Navigation.findNavController(view).navigate(EpisodeListFragmentDirections.actionEpisodeListFragmentToEpisodeInfoFragment(
                item
            ))
        }
        fragmentEpisodeListBinding.episodesRecyclerView.layoutManager =
            LinearLayoutManager(activity?.applicationContext)
        fragmentEpisodeListBinding.episodesRecyclerView.adapter = episodesListAdapter


        episodesViewModel.getEpisodes().observe(viewLifecycleOwner, {
            episodesListAdapter.submitList(it.episodes)
        })
//        fragmentEpisodeListBinding.button.setOnClickListener{
//            if(pagesLoaded != pagesTotal){
//                pagesLoaded++
//                episodesViewModel.retrieveEpisodes(pagesLoaded)
//                if(pagesLoaded == pagesTotal){
//                    fragmentEpisodeListBinding.button.isEnabled = false
//                }
//            }
//        }

    }
}