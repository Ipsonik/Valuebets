package com.example.valuebetsapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.valuebetsapp.databinding.ItemBetBinding

class BetListAdapter : ListAdapter<Bet, BetListAdapter.BetViewHolder>(DiffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BetViewHolder {
        return BetViewHolder(
            ItemBetBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: BetViewHolder, position: Int) {
        val bet = getItem(position)
        holder.bind(bet)
    }

    inner class BetViewHolder(private var binding: ItemBetBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(bet: Bet) {
            binding.bet = bet
            binding.apply {
                valueTv.text = bet.value
                eventTv.text = bet.event
                marketTv.text = bet.market
                oddTv.text = bet.odd
            }
            binding.executePendingBindings()

        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Bet>() {
        override fun areItemsTheSame(oldItem: Bet, newItem: Bet): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Bet, newItem: Bet): Boolean {
            return oldItem == newItem
        }

    }
}