package com.example.rickmortyalbum.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rickmortyalbum.adapter.EpisodesPagingListAdapter
import com.example.rickmortyalbum.databinding.FragmentEpisodeListBinding
import com.example.rickmortyalbum.viewmodel.EpisodesViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.viewModel

class EpisodeListFragment : Fragment() {

    private lateinit var fragmentEpisodeListBinding: FragmentEpisodeListBinding
    private lateinit var episodesPagingListAdapter: EpisodesPagingListAdapter
    private val episodesViewModel: EpisodesViewModel by viewModel()
    private val disposables : CompositeDisposable = CompositeDisposable()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentEpisodeListBinding = FragmentEpisodeListBinding.inflate(layoutInflater)
        return fragmentEpisodeListBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fragmentEpisodeListBinding.episodesRecyclerView.layoutManager = LinearLayoutManager(activity?.applicationContext)
        fragmentEpisodeListBinding.episodesRecyclerView.adapter = episodesPagingListAdapter
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        RxJavaPlugins.setErrorHandler { e: Throwable? ->
            Log.d("LOL" ,"TF IS THIS! ${e.toString()}")
        }
        episodesPagingListAdapter = EpisodesPagingListAdapter{item->
            view?.let {
                Navigation.findNavController(it).navigate(EpisodeListFragmentDirections.actionEpisodeListFragmentToEpisodeInfoFragment(
                    item
                ))
            }
        }
        disposables.add(episodesViewModel.getEpisodes().subscribeOn(Schedulers.io()).observeOn(
            Schedulers.io())
            .subscribe({
                Log.d("LOL", it.toString())
                episodesPagingListAdapter.submitData(viewLifecycleOwner.lifecycle, it)
            }, {
                Log.d("LOL", it.toString())
            }, {
                TODO()
            }))

    }
    override fun onDestroy() {
        super.onDestroy()
        disposables.dispose()
    }
}