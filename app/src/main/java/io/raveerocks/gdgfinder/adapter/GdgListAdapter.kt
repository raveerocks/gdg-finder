package io.raveerocks.gdgfinder.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import io.raveerocks.gdgfinder.databinding.ListItemBinding
import io.raveerocks.gdgfinder.network.GdgChapter

class GdgListAdapter(private val clickListener: GdgClickListener) :
    ListAdapter<GdgChapter, GdgListAdapter.GdgListViewHolder>(
        DiffCallback
    ) {

    companion object DiffCallback : DiffUtil.ItemCallback<GdgChapter>() {
        override fun areItemsTheSame(oldItem: GdgChapter, newItem: GdgChapter): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: GdgChapter, newItem: GdgChapter): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GdgListViewHolder {
        return GdgListViewHolder.from(parent)

    }

    override fun onBindViewHolder(holder: GdgListViewHolder, position: Int) {
        holder.bind(clickListener, getItem(position))
    }

    class GdgListViewHolder(private var binding: ListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        companion object {
            fun from(parent: ViewGroup): GdgListViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemBinding.inflate(layoutInflater, parent, false)
                return GdgListViewHolder(binding)
            }
        }

        fun bind(listener: GdgClickListener, gdgChapter: GdgChapter) {
            binding.chapter = gdgChapter
            binding.clickListener = listener
            binding.executePendingBindings()
        }
    }

}