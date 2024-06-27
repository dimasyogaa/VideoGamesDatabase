package com.yogadimas.videogamesdatabaseyogadimaspambudi.ui.favorite

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.yogadimas.videogamesdatabaseyogadimaspambudi.databinding.FragmentFavoriteBinding
import com.yogadimas.videogamesdatabaseyogadimaspambudi.ui.adapter.GamesListAdapter
import com.yogadimas.videogamesdatabaseyogadimaspambudi.ui.detail.DetailActivity
import com.yogadimas.videogamesdatabaseyogadimaspambudi.ui.factory.ViewModelFactory
import com.yogadimas.videogamesdatabaseyogadimaspambudi.util.toResultsItemList


class FavoriteFragment : Fragment() {

    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!

    private val favoriteViewModel: FavoriteViewModel by activityViewModels {
        ViewModelFactory.getInstance(requireActivity())
    }

    private lateinit var adapter: GamesListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getAllFavoritesGames()
    }

    private fun getAllFavoritesGames() {
        binding.rvGames.layoutManager = LinearLayoutManager(requireActivity())
        adapter = GamesListAdapter {
            val intent = Intent(requireActivity(), DetailActivity::class.java)
            intent.putExtra(DetailActivity.KEY_EXTRA_ID, it.id)
            startActivity(intent)
        }
        binding.rvGames.adapter = adapter
        favoriteViewModel.getFavoriteGame().observe(viewLifecycleOwner) {
            adapter.submitList(it.toResultsItemList())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}