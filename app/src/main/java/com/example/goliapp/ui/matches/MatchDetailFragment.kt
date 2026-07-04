package com.example.goliapp.ui.matches

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.goliapp.R
import com.example.goliapp.databinding.FragmentMatchDetailBinding
import com.example.goliapp.utils.Resource
import com.example.goliapp.utils.toReadableDate
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

        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }

        viewModel.match.observe(viewLifecycleOwner) { result ->
            binding.progressBar.visibility =
                if (result is Resource.Loading) View.VISIBLE else View.GONE

            if (result is Resource.Success && result.data != null) {
                bindMatch(result.data)
            }
        }

        viewModel.isFavourite.observe(viewLifecycleOwner) { isFav ->
            binding.btnFavourite.setImageResource(
                if (isFav) R.drawable.ic_star_filled else R.drawable.ic_star_outline
            )
        }

        binding.btnFavourite.setOnClickListener { viewModel.toggleFavourite() }
    }

    private fun bindMatch(match: com.example.goliapp.domain.model.Match) {
        binding.apply {
            tvHomeName.text = match.homeTeamName
            tvAwayName.text = match.awayTeamName
            tvScore.text = match.scoreDisplay
            tvLeague.text = "${match.leagueName} · ${match.round}"
            tvMatchLeague.text = match.leagueName
            tvMatchRound.text = match.round
            tvMatchDate.text = match.date.toReadableDate()
            tvMatchStatus.text = match.statusLong

            if (match.isLive) {
                badgeLive.visibility = View.VISIBLE
                tvStatus.visibility = View.GONE
                tvLiveMinute.text = match.elapsed?.let { "LIVE · ${it}'" } ?: "LIVE"
            } else {
                badgeLive.visibility = View.GONE
                tvStatus.visibility = View.VISIBLE
                tvStatus.text = match.statusLong
            }

            if (match.isFinished && match.homeGoals != null && match.awayGoals != null) {
                tvFtHome.text = match.homeGoals.toString()
                tvFtAway.text = match.awayGoals.toString()
                tvHtHome.text = "-"
                tvHtAway.text = "-"
            } else {
                tvFtHome.text = "-"
                tvFtAway.text = "-"
                tvHtHome.text = "-"
                tvHtAway.text = "-"
            }

            Glide.with(ivHomeLogo.context).load(match.homeTeamLogo).into(ivHomeLogo)
            Glide.with(ivAwayLogo.context).load(match.awayTeamLogo).into(ivAwayLogo)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
