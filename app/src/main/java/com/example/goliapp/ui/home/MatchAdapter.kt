package com.example.goliapp.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.goliapp.R
import com.example.goliapp.databinding.ItemMatchCardBinding
import com.example.goliapp.domain.model.Match
import com.example.goliapp.utils.toMatchTime

class MatchAdapter(
    private val onMatchClick: (Match) -> Unit,
    private val onFavouriteClick: (Match) -> Unit
) : ListAdapter<Match, MatchAdapter.MatchViewHolder>(DIFF_CALLBACK) {

    private var favouriteIds = emptySet<Int>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatchViewHolder {
        val binding = ItemMatchCardBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return MatchViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MatchViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
    fun setFavouriteIds(ids: Set<Int>) {
        favouriteIds = ids
        notifyDataSetChanged()
    }

    inner class MatchViewHolder(private val binding: ItemMatchCardBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(match: Match) {
            binding.apply {
                tvHomeName.text = match.homeTeamName
                tvAwayName.text = match.awayTeamName
                tvLeagueName.text = match.leagueName

                when {
                    match.isLive -> {
                        badgeLive.visibility = View.VISIBLE
                        tvStatus.visibility = View.GONE
                        tvLiveMinute.text = match.elapsed?.let { "${it}'" } ?: "LIVE"
                        tvHomeScore.text = match.homeGoals?.toString() ?: "-"
                        tvAwayScore.text = match.awayGoals?.toString() ?: "-"
                    }
                    match.isFinished -> {
                        badgeLive.visibility = View.GONE
                        tvStatus.visibility = View.VISIBLE
                        tvStatus.text = match.statusShort
                        tvHomeScore.text = match.homeGoals?.toString() ?: "0"
                        tvAwayScore.text = match.awayGoals?.toString() ?: "0"
                    }
                    else -> {
                        badgeLive.visibility = View.GONE
                        tvStatus.visibility = View.VISIBLE
                        tvStatus.text = match.date.toMatchTime()
                        tvHomeScore.text = "-"
                        tvAwayScore.text = "-"
                    }
                }

                Glide.with(ivHomeLogo.context)
                    .load(match.homeTeamLogo)
                    .into(ivHomeLogo)

                Glide.with(ivAwayLogo.context)
                    .load(match.awayTeamLogo)
                    .into(ivAwayLogo)

                btnFavourite.setImageResource(
                    if (match.id in favouriteIds)
                        R.drawable.ic_star_filled
                    else
                        R.drawable.ic_star_outline
                )

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
