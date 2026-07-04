package com.example.goliapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.goliapp.databinding.FragmentHomeBinding
import com.example.goliapp.utils.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by viewModels()

    private lateinit var matchAdapter: MatchAdapter
    private lateinit var leagueAdapter: LeagueAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerViews()
        observeViewModel()

        binding.swipeRefresh.setOnRefreshListener { viewModel.refresh() }
    }

    private fun setupRecyclerViews() {
        matchAdapter = MatchAdapter(
            onMatchClick = { match ->
                val action = HomeFragmentDirections.actionHomeToMatchDetail(match.id)
                findNavController().navigate(action)
            },
            onFavouriteClick = { match -> viewModel.toggleFavouriteQuick(match) }
        )
        binding.rvMatches.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = matchAdapter
        }

        leagueAdapter = LeagueAdapter { league ->
            leagueAdapter.setSelected(league?.id)
            viewModel.onLeagueSelected(league?.id)
        }
        binding.rvLeagues.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = leagueAdapter
        }
    }

    private fun observeViewModel() {
        viewModel.matches.observe(viewLifecycleOwner) { result ->
            binding.swipeRefresh.isRefreshing = result is Resource.Loading
            when (result) {
                is Resource.Success -> {
                    matchAdapter.submitList(result.data)
                    binding.emptyState.root.visibility =
                        if (result.data.isNullOrEmpty()) View.VISIBLE else View.GONE
                    binding.rvMatches.visibility =
                        if (result.data.isNullOrEmpty()) View.GONE else View.VISIBLE
                }
                is Resource.Error -> {
                    binding.emptyState.root.visibility = View.VISIBLE
                }
                is Resource.Loading -> Unit
            }
        }

        viewModel.leagues.observe(viewLifecycleOwner) { result ->
            if (result is Resource.Success) {
                leagueAdapter.submitList(result.data)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun refreshFromMenu() {
        viewModel.refresh()
    }
}