package com.aditya.spacexrockets

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.aditya.spacexrockets.databinding.RocketItemBinding
//import com.atritripathi.musk.R
import com.aditya.spacexrockets.Rocket

import com.aditya.spacexrockets.RocketAdapter.RocketViewHolder
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

class RocketAdapter(private val listener: OnRocketClickListener) :
    ListAdapter<Rocket, RocketViewHolder>(ROCKET_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RocketViewHolder {
       // val binding = RocketItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val binding = RocketItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RocketViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RocketViewHolder, position: Int) {
        val currentRocket = getItem(position)
        currentRocket?.let { holder.bind(it) }
    }

    inner class RocketViewHolder(private val binding: RocketItemBinding) :
        ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val item = getItem(position)
                    item?.let { listener.onClick(it) }
                }
            }
        }

        fun bind(rocket: Rocket) {
            with(binding) {
                Glide.with(itemView)
                    .load(rocket.images[0])
                    .centerCrop()
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .error(R.drawable.ic_error)
                    .into(binding.ivRocket)
                tvRocketName.text = rocket.name
                tvCountry.text = rocket.country
               // tvEngines.text = rocket.engines
               // tvEngines.text = rocket.engines.toString()
              // tvDescription.text = rocket.description
            }
        }
    }

    interface OnRocketClickListener {
        fun onClick(rocket: Rocket)
    }

    companion object {
        private val ROCKET_COMPARATOR = object : DiffUtil.ItemCallback<Rocket>() {
            override fun areItemsTheSame(oldItem: Rocket, newItem: Rocket) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Rocket, newItem: Rocket) =
                oldItem == newItem
        }
    }
}