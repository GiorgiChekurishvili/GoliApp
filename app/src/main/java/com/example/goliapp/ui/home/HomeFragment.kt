package com.example.goliapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.goliapp.R
import com.example.goliapp.databinding.FragmentHomeBinding
import com.example.goliapp.repository.FavouritesRepository
import com.example.goliapp.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by viewModels()

    @Inject
    lateinit var favouritesRepository: FavouritesRepository

    private lateinit var matchAdapter: MatchAdapter
    private lateinit var leagueAdapter: LeagueAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAdapters()
        setupLeagues()
        observeMatches()
        setupSwipeRefresh()
    }

    private fun setupAdapters() {
        matchAdapter = MatchAdapter(
            onMatchClick = { match ->
                val action = HomeFragmentDirections.actionHomeToMatchDetail(match.id)
                findNavController().navigate(action)
            },
            onFavouriteClick = { match ->
                viewLifecycleOwner.lifecycleScope.launch {
                    favouritesRepository.isFavourite(match.id).collectLatest { isFav ->
                        if (isFav) favouritesRepository.removeFavourite(match.id)
                        else favouritesRepository.addFavourite(match)
                    }
                }
            }
        )
        binding.rvMatches.apply {
            adapter = matchAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        // Observe favourites to update star icons
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                favouritesRepository.getAllFavourites().collectLatest { favs ->
                    matchAdapter.setFavourites(favs.map { it.id }.toSet())
                }
            }
        }
    }

    private fun setupLeagues() {
        leagueAdapter = LeagueAdapter { league ->
            leagueAdapter.setSelected(league.id)
            viewModel.selectLeague(if (league.id == 0) null else league.id)
        }
        binding.rvLeagues.apply {
            adapter = leagueAdapter
            layoutManager = LinearLayoutManager(
                requireContext(), LinearLayoutManager.HORIZONTAL, false
            )
        }
        leagueAdapter.submitList(viewModel.leagues)
        leagueAdapter.setSelected(0)
    }

    private fun observeMatches() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.matches.collectLatest { resource ->
                    when (resource) {
                        is Resource.Loading -> {
                            binding.progressBar.visibility = View.VISIBLE
                            binding.emptyState.root.visibility = View.GONE
                        }
                        is Resource.Success -> {
                            binding.progressBar.visibility = View.GONE
                            binding.swipeRefresh.isRefreshing = false
                            val matches = resource.data
                            matchAdapter.submitList(matches)
                            binding.emptyState.root.visibility =
                                if (matches.isEmpty()) View.VISIBLE else View.GONE
                        }
                        is Resource.Error -> {
                            binding.progressBar.visibility = View.GONE
                            binding.swipeRefresh.isRefreshing = false
                        }
                    }
                }
            }
        }
    }

    private fun setupSwipeRefresh() {
        binding.swipeRefresh.setColorSchemeColors(
            requireContext().getColor(R.color.pitch_green)
        )
        binding.swipeRefresh.setOnRefreshListener {
            viewModel.loadTodayMatches()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}