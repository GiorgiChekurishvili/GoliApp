package com.example.goliapp.ui.standings

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.goliapp.databinding.ItemStandingBinding
import com.example.goliapp.domain.model.Standing

class StandingsAdapter : ListAdapter<Standing, StandingsAdapter.StandingViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StandingViewHolder {
        val binding = ItemStandingBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return StandingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StandingViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class StandingViewHolder(private val binding: ItemStandingBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(standing: Standing) {
            binding.apply {
                tvRank.text = standing.rank.toString()
                tvTeamName.text = standing.teamName
                tvPlayed.text = standing.played.toString()
                tvGoalDiff.text = if (standing.goalsDiff >= 0) "+${standing.goalsDiff}" else "${standing.goalsDiff}"
                tvPoints.text = standing.points.toString()

                Glide.with(ivTeamLogo.context)
                    .load(standing.teamLogo)
                    .into(ivTeamLogo)
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Standing>() {
            override fun areItemsTheSame(oldItem: Standing, newItem: Standing) =
                oldItem.teamName == newItem.teamName

            override fun areContentsTheSame(oldItem: Standing, newItem: Standing) =
                oldItem == newItem
        }
    }
}
