package com.example.rickmortyalbum.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.rickmortyalbum.adapter.CharactersListAdapter
import com.example.rickmortyalbum.adapter.CharactersPagingListAdapter
import com.example.rickmortyalbum.data.CharactersPagingSource
import com.example.rickmortyalbum.databinding.FragmentEpisodeInfoBinding
import com.example.rickmortyalbum.db.CharactersDB
import com.example.rickmortyalbum.viewmodel.CharactersViewModel

class EpisodeInfoFragment : Fragment() {
    private val viewModel: CharactersViewModel by viewModels()
    private val args: EpisodeInfoFragmentArgs by navArgs()
    private lateinit var charactersListAdapter: CharactersListAdapter
    private lateinit var fragmentEpisodeInfoBinding: FragmentEpisodeInfoBinding
    private var db: CharactersDB? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentEpisodeInfoBinding = FragmentEpisodeInfoBinding.inflate(layoutInflater)
        return fragmentEpisodeInfoBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Todo ბაზას აქ არ უნდა ქმნიდე, ცალკე უნდა გქონდეს როგორც რეთრაივერია, რეპოზიტორი და ვიუმოდელიდან იძახებდე მეთოდს, მერე ფრაგმენტშ ლაივდატით დაააბრუნებ
        db = activity?.applicationContext?.let {
            Room.databaseBuilder(
                it,
                CharactersDB::class.java, "database-name"
            ).build()
        }
        charactersListAdapter = CharactersListAdapter() {
            Navigation.findNavController(view).navigate(EpisodeInfoFragmentDirections.actionEpisodeInfoFragmentToCharacterInfoFragment(it))
        }
        fragmentEpisodeInfoBinding.recyclerView.adapter = charactersListAdapter
        fragmentEpisodeInfoBinding.recyclerView.layoutManager =
            LinearLayoutManager(activity?.applicationContext)
        viewModel.progressBar = fragmentEpisodeInfoBinding.simpleProgressBar
        initInfoPage()
    }

    private fun initInfoPage() {
        fragmentEpisodeInfoBinding.episodeNameTV.text = args.episode.name
        fragmentEpisodeInfoBinding.episodeAirDateTV.text = args.episode.air_date
        fragmentEpisodeInfoBinding.episodeTV.text = args.episode.episode
        Log.d("LOL", "REQUEST")
        viewModel.charactersData.observe(viewLifecycleOwner, {
            charactersListAdapter.submitList(it)
            fragmentEpisodeInfoBinding.simpleProgressBar.visibility = View.INVISIBLE
        })
        Log.d("LOL", "REQUEST")
        if (db == null){
            Log.d("LOL", "DB IS NULL")
        }

        db?.let {

            viewModel.getCharactersWithID(args.episode.characters, it) }

    }
}