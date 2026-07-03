package com.example.goliapp.ui.matches

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.goliapp.R
import com.example.goliapp.databinding.FragmentMatchDetailBinding
import com.example.goliapp.utils.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MatchDetailFragment : Fragment() {

    private var _binding: FragmentMatchDetailBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MatchDetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMatchDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.match.observe(viewLifecycleOwner) { result ->
            binding.progressBar.visibility = if (result is Resource.Loading) View.VISIBLE else View.GONE
            if (result is Resource.Success && result.data != null) {
                val match = result.data
                binding.apply {
                    tvHomeTeam.text = match.homeTeamName
                    tvAwayTeam.text = match.awayTeamName
                    tvScore.text = "${match.homeScore ?: 0} - ${match.awayScore ?: 0}"
                    tvLeagueName.text = match.leagueName
                    tvStatus.text = match.status
                    tvDate.text = match.date

                    Glide.with(ivHomeLogo.context).load(match.homeTeamLogo).into(ivHomeLogo)
                    Glide.with(ivAwayLogo.context).load(match.awayTeamLogo).into(ivAwayLogo)
                }
            }
        }

        viewModel.isFavourite.observe(viewLifecycleOwner) { isFav ->
            binding.btnFavourite.setImageResource(
                if (isFav) R.drawable.ic_favourite_filled else R.drawable.ic_favourite_outline
            )
        }

        binding.btnFavourite.setOnClickListener { viewModel.toggleFavourite() }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}