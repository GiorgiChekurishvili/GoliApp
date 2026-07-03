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

class MatchAdapter(
    private val onMatchClick: (Match) -> Unit,
    private val onFavouriteClick: (Match) -> Unit
) : ListAdapter<Match, MatchAdapter.MatchViewHolder>(DiffCallback()) {

    private val favouriteIds = mutableSetOf<Int>()

    fun setFavourites(ids: Set<Int>) {
        favouriteIds.clear()
        favouriteIds.addAll(ids)
        notifyDataSetChanged()
    }

    inner class MatchViewHolder(
        private val binding: ItemMatchCardBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(match: Match) {
            // Team names
            binding.tvHomeName.text = match.homeTeamName
            binding.tvAwayName.text = match.awayTeamName

            // Team logos
            Glide.with(binding.root).load(match.homeTeamLogo).into(binding.ivHomeLogo)
            Glide.with(binding.root).load(match.awayTeamLogo).into(binding.ivAwayLogo)

            // League
            binding.tvLeagueName.text = match.leagueName

            // Score / status
            if (match.isLive) {
                binding.badgeLive.visibility = View.VISIBLE
                binding.tvStatus.visibility = View.GONE
                binding.tvLiveMinute.text = "${match.elapsed}'"
                binding.tvHomeScore.text = match.homeGoals?.toString() ?: "0"
                binding.tvAwayScore.text = match.awayGoals?.toString() ?: "0"
            } else if (match.isFinished) {
                binding.badgeLive.visibility = View.GONE
                binding.tvStatus.visibility = View.VISIBLE
                binding.tvStatus.text = "FT"
                binding.tvHomeScore.text = match.homeGoals?.toString() ?: "-"
                binding.tvAwayScore.text = match.awayGoals?.toString() ?: "-"
            } else {
                // Scheduled
                binding.badgeLive.visibility = View.GONE
                binding.tvStatus.visibility = View.VISIBLE
                binding.tvStatus.text = match.date.toMatchTime()
                binding.tvHomeScore.text = "-"
                binding.tvAwayScore.text = "-"
            }

            // Favourite icon
            val isFav = match.id in favouriteIds
            binding.btnFavourite.setImageResource(
                if (isFav) R.drawable.ic_star_filled else R.drawable.ic_star_outline
            )

            binding.btnFavourite.setOnClickListener { onFavouriteClick(match) }
            binding.root.setOnClickListener { onMatchClick(match) }
        }

        private fun String.toMatchTime(): String {
            return try {
                val inputFormat = java.text.SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX", java.util.Locale.getDefault())
                val outputFormat = java.text.SimpleDateFormat("HH:mm", java.util.Locale.getDefault())
                outputFormat.timeZone = java.util.TimeZone.getDefault()
                val date = inputFormat.parse(this) ?: return this
                outputFormat.format(date)
            } catch (e: Exception) { this }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatchViewHolder {
        val binding = ItemMatchCardBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return MatchViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MatchViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class DiffCallback : DiffUtil.ItemCallback<Match>() {
        override fun areItemsTheSame(oldItem: Match, newItem: Match) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Match, newItem: Match) = oldItem == newItem
    }
}