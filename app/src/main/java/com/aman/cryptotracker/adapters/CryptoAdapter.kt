package com.aman.cryptotracker.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.aman.cryptotracker.R
import com.aman.cryptotracker.databinding.ItemCryptoBinding
import com.aman.cryptotracker.models.Crypto
import com.bumptech.glide.Glide


class CryptoAdapter :
    ListAdapter<Crypto, CryptoAdapter.CryptoViewHolder>(DiffCallback()) {

    class CryptoViewHolder(
        private val binding: ItemCryptoBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(crypto: Crypto) {

            binding.tvCoinName.text = crypto.name
            binding.tvSymbol.text = crypto.symbol.uppercase()

            binding.tvPrice.text =
                crypto.currentPrice?.let {
                    "₹${"%,.2f".format(it)}"
                } ?: "N/A"

            val change = crypto.priceChangePercentage24h

            binding.tvPriceChange.text =
                change?.let { "%.2f%%".format(it) } ?: "N/A"

            binding.tvPriceChange.setTextColor(
                ContextCompat.getColor(
                    binding.root.context,
                    if ((change ?: 0.0) >= 0) {
                        R.color.green
                    } else {
                        R.color.red
                    }
                )
            )

            Glide.with(binding.ivCoin)
                .load(crypto.image)
                .placeholder(R.drawable.ic_launcher_background)
                .into(binding.ivCoin)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CryptoViewHolder {

        val binding = ItemCryptoBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return CryptoViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: CryptoViewHolder,
        position: Int
    ) {
        holder.bind(getItem(position))
    }

    class DiffCallback : DiffUtil.ItemCallback<Crypto>() {

        override fun areItemsTheSame(
            oldItem: Crypto,
            newItem: Crypto
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: Crypto,
            newItem: Crypto
        ): Boolean {
            return oldItem == newItem
        }
    }
}
