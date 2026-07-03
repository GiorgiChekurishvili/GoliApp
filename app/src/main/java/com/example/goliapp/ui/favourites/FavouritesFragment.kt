package com.example.goliapp.ui.favourites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.goliapp.databinding.FragmentFavouritesBinding
import com.example.goliapp.ui.home.MatchAdapter
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class FavouritesFragment : Fragment() {

    private var _binding: FragmentFavouritesBinding? = null
    private val binding get() = _binding!!

    private val viewModel: FavouritesViewModel by viewModels()
    private lateinit var favouritesAdapter: MatchAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavouritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        favouritesAdapter = MatchAdapter(
            onMatchClick = { match ->
                val action = FavouritesFragmentDirections.actionFavouritesToMatchDetail(match.id)
                findNavController().navigate(action)
            },
            onFavouriteClick = { match -> viewModel.removeFavourite(match) }
        )

        binding.rvFavourites.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = favouritesAdapter
        }

        viewModel.favourites.observe(viewLifecycleOwner) { list ->
            favouritesAdapter.submitList(list)
            binding.layoutEmpty.visibility = if (list.isEmpty()) View.VISIBLE else View.GONE
            binding.rvFavourites.visibility = if (list.isEmpty()) View.GONE else View.VISIBLE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}