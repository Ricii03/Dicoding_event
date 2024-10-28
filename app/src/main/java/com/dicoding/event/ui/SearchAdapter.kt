package com.dicoding.event.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.event.data.response.ListEventsItem
import com.dicoding.event.databinding.ItemSearchLayoutBinding


class SearchAdapter(private val onClick: (ListEventsItem) -> Unit) :
    ListAdapter<ListEventsItem, SearchAdapter.EventViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val view =
            ItemSearchLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EventViewHolder(view, onClick)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val event = getItem(position)
        holder.bind(event)
    }

    class EventViewHolder(
        private var binding: ItemSearchLayoutBinding,
        val onClick: (ListEventsItem) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(eventItem: ListEventsItem) {
            Glide.with(binding.root).load(eventItem.imageLogo).into(binding.imgMediaCover)
            binding.tvName.text = eventItem.name

            itemView.setOnClickListener {
                onClick(eventItem)
            }
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ListEventsItem>() {
            override fun areItemsTheSame(
                oldItem: ListEventsItem,
                newItem: ListEventsItem
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: ListEventsItem,
                newItem: ListEventsItem
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}