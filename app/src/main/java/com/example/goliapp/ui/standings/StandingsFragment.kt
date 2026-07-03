package com.example.goliapp.ui.standings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.goliapp.databinding.FragmentStandingsBinding
import com.example.goliapp.utils.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StandingsFragment : Fragment() {

    private var _binding: FragmentStandingsBinding? = null
    private val binding get() = _binding!!

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
                    binding.root.visibility =
                        if (result.data.isNullOrEmpty()) View.VISIBLE else View.GONE
                }
                is Resource.Error -> binding.root.visibility = View.VISIBLE
                is Resource.Loading -> Unit
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}