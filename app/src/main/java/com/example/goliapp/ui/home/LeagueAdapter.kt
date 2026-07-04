package com.example.goliapp.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.goliapp.databinding.ItemLeagueChipBinding
import com.example.goliapp.domain.model.League

class LeagueAdapter(
    private val onLeagueClick: (League?) -> Unit
) : ListAdapter<League, LeagueAdapter.LeagueViewHolder>(DIFF_CALLBACK) {

    private var selectedId: Int? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LeagueViewHolder {
        val binding = ItemLeagueChipBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return LeagueViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LeagueViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    fun setSelected(leagueId: Int?) {
        val previous = selectedId
        selectedId = leagueId
        notifyItemChanged(currentList.indexOfFirst { it.id == previous })
        notifyItemChanged(currentList.indexOfFirst { it.id == leagueId })
    }

    inner class LeagueViewHolder(private val binding: ItemLeagueChipBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(league: League) {
            binding.apply {
                tvLeagueChipName.text = league.name
                Glide.with(ivLeagueLogo.context)
                    .load(league.logo)
                    .into(ivLeagueLogo)

                root.isSelected = league.id == selectedId
                root.setOnClickListener {
                    onLeagueClick(if (league.id == selectedId) null else league)
                }
            }

        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<League>() {
            override fun areItemsTheSame(oldItem: League, newItem: League) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: League, newItem: League) =
                oldItem == newItem
        }
    }
}
