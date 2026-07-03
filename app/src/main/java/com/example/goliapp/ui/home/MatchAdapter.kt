package com.example.goliapp.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.goliapp.databinding.ItemMatchBinding
import com.example.goliapp.domain.model.Match

class MatchAdapter(
    private val onMatchClick: (Match) -> Unit,
    private val onFavouriteClick: (Match) -> Unit
) : ListAdapter<Match, MatchAdapter.MatchViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatchViewHolder {
        val binding = ItemMatchBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return MatchViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MatchViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class MatchViewHolder(private val binding: ItemMatchBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(match: Match) {
            binding.apply {
                tvHomeTeam.text = match.homeTeamName
                tvAwayTeam.text = match.awayTeamName
                tvLeagueName.text = match.leagueName
                tvMatchStatus.text = match.status

                if (match.status.equals("LIVE", ignoreCase = true) ||
                    match.status.equals("1H", ignoreCase = true) ||
                    match.status.equals("2H", ignoreCase = true)
                ) {
                    liveDot.visibility = android.view.View.VISIBLE
                    tvScore.text = "${match.homeScore ?: 0} - ${match.awayScore ?: 0}"
                } else if (match.status.equals("FT", ignoreCase = true)) {
                    liveDot.visibility = android.view.View.GONE
                    tvScore.text = "${match.homeScore ?: 0} - ${match.awayScore ?: 0}"
                } else {
                    liveDot.visibility = android.view.View.GONE
                    tvScore.text = match.date.takeLast(5) // HH:mm
                }

                Glide.with(ivHomeLogo.context)
                    .load(match.homeTeamLogo)
                    .into(ivHomeLogo)

                Glide.with(ivAwayLogo.context)
                    .load(match.awayTeamLogo)
                    .into(ivAwayLogo)

                root.setOnClickListener { onMatchClick(match) }
                btnFavourite.setOnClickListener { onFavouriteClick(match) }
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Match>() {
            override fun areItemsTheSame(oldItem: Match, newItem: Match) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Match, newItem: Match) =
                oldItem == newItem
        }
    }
}