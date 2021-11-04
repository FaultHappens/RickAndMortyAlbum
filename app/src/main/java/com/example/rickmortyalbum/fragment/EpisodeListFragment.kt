package com.example.rickmortyalbum.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rickmortyalbum.adapter.EpisodesPagingListAdapter
import com.example.rickmortyalbum.databinding.FragmentEpisodeListBinding
import com.example.rickmortyalbum.viewmodel.EpisodesViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.viewModel

class EpisodeListFragment : Fragment() {

    private lateinit var fragmentEpisodeListBinding: FragmentEpisodeListBinding
    private lateinit var episodesPagingListAdapter: EpisodesPagingListAdapter
    private val episodesViewModel: EpisodesViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentEpisodeListBinding = FragmentEpisodeListBinding.inflate(layoutInflater)
        return fragmentEpisodeListBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        episodesPagingListAdapter = EpisodesPagingListAdapter{item->
            Navigation.findNavController(view).navigate(EpisodeListFragmentDirections.actionEpisodeListFragmentToEpisodeInfoFragment(
                item
            ))
        }
        fragmentEpisodeListBinding.episodesRecyclerView.layoutManager = LinearLayoutManager(activity?.applicationContext)
        fragmentEpisodeListBinding.episodesRecyclerView.adapter = episodesPagingListAdapter
        viewLifecycleOwner.lifecycleScope.launch {
            episodesViewModel.getEpisodes().collectLatest { episodes ->
                episodesPagingListAdapter.submitData(episodes)
            }
        }
    }
}