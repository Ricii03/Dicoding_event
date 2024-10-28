package com.dicoding.event.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.event.R
import com.dicoding.event.data.local.EventEntity
import com.dicoding.event.databinding.ItemEventBinding

class EventListAdapter(
    private val onClick: (EventEntity) -> Unit
) : ListAdapter<EventEntity, EventListAdapter.EventViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val view = ItemEventBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EventViewHolder(view, onClick)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val event = getItem(position)
        holder.bind(event)
    }

    class EventViewHolder(
        private var binding: ItemEventBinding,
        val onClick: (EventEntity) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(eventItem: EventEntity) {
            binding.tvTitle.text = eventItem.name
            Glide.with(itemView.context)
                .load(eventItem.mediaCover)
                .placeholder(R.drawable.ic_loading)
                .error(R.drawable.ic_error)
                .into(binding.ivImage)

            itemView.setOnClickListener {
                onClick(eventItem)
            }
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<EventEntity>() {
            override fun areItemsTheSame(
                oldItem: EventEntity,
                newItem: EventEntity
            ): Boolean {
                return oldItem == newItem
            }

            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(
                oldItem: EventEntity,
                newItem: EventEntity
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}
