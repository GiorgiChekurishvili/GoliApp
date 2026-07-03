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
    private val onLeagueClick: (League) -> Unit
) : ListAdapter<League, LeagueAdapter.LeagueViewHolder>(DiffCallback()) {

    private var selectedId: Int = 0

    fun setSelected(id: Int) {
        selectedId = id
        notifyDataSetChanged()
    }

    inner class LeagueViewHolder(
        private val binding: ItemLeagueChipBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(league: League) {
            binding.tvLeagueChipName.text = league.name

            if (league.logo.isNotEmpty()) {
                Glide.with(binding.root)
                    .load(league.logo)
                    .into(binding.ivLeagueLogo)
            }

            val isSelected = league.id == selectedId
            binding.root.alpha = if (isSelected) 1f else 0.6f

            binding.root.setOnClickListener {
                onLeagueClick(league)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LeagueViewHolder {
        val binding = ItemLeagueChipBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return LeagueViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LeagueViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class DiffCallback : DiffUtil.ItemCallback<League>() {
        override fun areItemsTheSame(oldItem: League, newItem: League) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: League, newItem: League) = oldItem == newItem
    }
}