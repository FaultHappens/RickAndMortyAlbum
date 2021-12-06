package com.example.rickmortyalbum.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rickmortyalbum.adapter.CharactersPagingListAdapter
import com.example.rickmortyalbum.databinding.FragmentCharactersListBinding
import com.example.rickmortyalbum.viewmodel.CharactersViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.koin.android.viewmodel.ext.android.viewModel


class CharactersListFragment : Fragment() {


    private lateinit var fragmentCharactersListBinding: FragmentCharactersListBinding
    private lateinit var charactersPagingListAdapter: CharactersPagingListAdapter
    private val charactersViewModel: CharactersViewModel by viewModel()
    private val disposables: CompositeDisposable = CompositeDisposable()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentCharactersListBinding = FragmentCharactersListBinding.inflate(layoutInflater)
        return fragmentCharactersListBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fragmentCharactersListBinding.charactersRecyclerView.layoutManager =
            LinearLayoutManager(activity?.applicationContext)
        fragmentCharactersListBinding.charactersRecyclerView.adapter = charactersPagingListAdapter


    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        RxJavaPlugins.setErrorHandler { e: Throwable? ->
            Log.d("LOL", "TF IS THIS! ${e.toString()}")
        }
        charactersPagingListAdapter = CharactersPagingListAdapter {
            view?.let { it1 ->
                Navigation.findNavController(it1).navigate(
                    CharactersListFragmentDirections.actionCharactersListFragmentToCharacterInfoFragment(
                        it
                    )
                )
            }
        }
        disposables.add(charactersViewModel.getCharacters().subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribe({
                Log.d("LOL", it.toString())
                charactersPagingListAdapter.submitData(viewLifecycleOwner.lifecycle, it)
            }, {
                Log.d("LOL", it.toString())
            }, {
                TODO()
            })
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        disposables.dispose()
    }
}
