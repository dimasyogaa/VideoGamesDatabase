package com.yogadimas.videogamesdatabaseyogadimaspambudi.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.yogadimas.videogamesdatabaseyogadimaspambudi.databinding.FragmentHomeBinding
import com.yogadimas.videogamesdatabaseyogadimaspambudi.ui.adapter.GamesListAdapter
import com.yogadimas.videogamesdatabaseyogadimaspambudi.ui.adapter.GamesListPagingAdapter
import com.yogadimas.videogamesdatabaseyogadimaspambudi.ui.adapter.LoadingStateAdapter
import com.yogadimas.videogamesdatabaseyogadimaspambudi.ui.detail.DetailActivity
import com.yogadimas.videogamesdatabaseyogadimaspambudi.ui.factory.ViewModelFactory
import com.yogadimas.videogamesdatabaseyogadimaspambudi.util.Resource


class HomeFragment : Fragment() {


    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val homeViewModel: HomeViewModel by activityViewModels {
        ViewModelFactory.getInstance(requireActivity())
    }

    private lateinit var adapter: GamesListAdapter
    private lateinit var adapterPaging: GamesListPagingAdapter

    private var isSearching = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        binding.apply {

            searchView.setupWithSearchBar(searchBar)

            searchView
                .editText
                .setOnEditorActionListener { _, _, _ ->

                    searchBar.setText(searchView.text)
                    homeViewModel.getSearchGame(searchView.text.toString().trim())
                        .observe(viewLifecycleOwner) {
                            when (it) {
                                is Resource.Error -> {
                                    showLoading(false)
                                    Toast.makeText(
                                        requireActivity(),
                                        it.message,
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }

                                is Resource.Loading -> showLoading(true)
                                is Resource.Success -> {
                                    showLoading(false)
                                    adapter.submitList(it.data?.results)
                                }
                            }
                        }

                    searchView.hide()

                    isSearching = true
                    false
                }
        }

        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {

                binding.apply {
                    if (searchView.isShowing) {
                        searchView.hide()
                    } else {
                        requireActivity().finish()
                    }
                }

            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(requireActivity(), callback)
        binding.rvGames.layoutManager = LinearLayoutManager(requireActivity())
        getAllGames()
        // getAllGamesPaging()
    }

    private fun getAllGames() {
        adapter = GamesListAdapter {
            val intent = Intent(requireActivity(), DetailActivity::class.java)
            intent.putExtra(DetailActivity.KEY_EXTRA_ID, it.id)
            startActivity(intent)
        }
        binding.rvGames.adapter = adapter

        homeViewModel.getAllGames().observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Error -> {
                    showLoading(false)
                    Toast.makeText(requireActivity(), it.message, Toast.LENGTH_SHORT).show()
                }

                is Resource.Loading -> showLoading(true)
                is Resource.Success -> {
                    showLoading(false)
                    adapter.submitList(it.data?.results)
                }
            }
        }
    }

    private fun getAllGamesPaging() {
        adapterPaging = GamesListPagingAdapter {
            val intent = Intent(requireActivity(), DetailActivity::class.java)
            intent.putExtra(DetailActivity.KEY_EXTRA_ID, it.id)
            startActivity(intent)
        }
        binding.rvGames.adapter = adapterPaging.withLoadStateFooter(
            footer = LoadingStateAdapter {
                adapterPaging.retry()
            }
        )
        homeViewModel.getAllGamesPaging().observe(requireActivity()) {
            adapterPaging.submitData(lifecycle, it)
        }
    }

    private fun showLoading(boolean: Boolean) {
        binding.mainProgressBar.visibility = if (boolean) View.VISIBLE else View.GONE
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}