package com.example.goliapp.ui.standings

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.goliapp.R
import com.example.goliapp.databinding.ItemStandingRowBinding
import com.example.goliapp.domain.model.Standing

class StandingsAdapter : ListAdapter<Standing, StandingsAdapter.StandingViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StandingViewHolder {
        val binding = ItemStandingRowBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return StandingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StandingViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class StandingViewHolder(private val binding: ItemStandingRowBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(standing: Standing) {
            binding.apply {
                tvRank.text = standing.rank.toString()
                tvTeamName.text = standing.teamName
                tvPlayed.text = standing.played.toString()
                tvGoalDiff.text = if (standing.goalDifference >= 0) {
                    "+${standing.goalDifference}"
                } else {
                    standing.goalDifference.toString()
                }
                tvPoints.text = standing.points.toString()

                val barColor = when {
                    standing.rank <= 4 -> R.color.win_green
                    standing.rank >= currentList.size - 2 -> R.color.loss_red
                    else -> R.color.draw_gray
                }
                viewPositionBar.setBackgroundColor(
                    ContextCompat.getColor(root.context, barColor)
                )

                Glide.with(ivTeamLogo.context)
                    .load(standing.teamLogo)
                    .into(ivTeamLogo)
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Standing>() {
            override fun areItemsTheSame(oldItem: Standing, newItem: Standing) =
                oldItem.teamId == newItem.teamId

            override fun areContentsTheSame(oldItem: Standing, newItem: Standing) =
                oldItem == newItem
        }
    }
}
