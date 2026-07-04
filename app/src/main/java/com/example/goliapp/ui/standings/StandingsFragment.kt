package com.example.goliapp.ui.standings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.goliapp.R
import com.example.goliapp.databinding.FragmentStandingsBinding
import com.example.goliapp.utils.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StandingsFragment : Fragment() {

    private var _binding: FragmentStandingsBinding? = null
    private val binding get() = _binding!!

    private val args: StandingsFragmentArgs by navArgs()
    private val viewModel: StandingsViewModel by viewModels()
    private lateinit var standingsAdapter: StandingsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStandingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.updateLeague(args.leagueId)

        standingsAdapter = StandingsAdapter()
        binding.rvStandings.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = standingsAdapter
        }

        binding.swipeRefresh.setOnRefreshListener { viewModel.refresh() }

        viewModel.standings.observe(viewLifecycleOwner) { result ->
            binding.swipeRefresh.isRefreshing = result is Resource.Loading
            when (result) {
                is Resource.Success -> {
                    standingsAdapter.submitList(result.data)
                    val isEmpty = result.data.isNullOrEmpty()
                    binding.emptyState.root.visibility = if (isEmpty) View.VISIBLE else View.GONE
                    binding.rvStandings.visibility = if (isEmpty) View.GONE else View.VISIBLE

                    if (isEmpty) {
                        binding.emptyState.tvEmptyTitle.text = getString(R.string.error_loading)
                        binding.emptyState.tvEmptySubtitle.text = getString(R.string.select_league)
                    }
                }
                is Resource.Error -> {
                    binding.emptyState.root.visibility = View.VISIBLE
                    binding.rvStandings.visibility = View.GONE
                    binding.emptyState.tvEmptyTitle.text = getString(R.string.error_loading)
                    binding.emptyState.tvEmptySubtitle.text = result.message ?: ""
                }
                is Resource.Loading -> Unit
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    fun changeLeague(leagueId: Int) {
        viewModel.loadStandings(leagueId)
    }

    fun refreshFromMenu() {
        viewModel.refresh()
    }
}