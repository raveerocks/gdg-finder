package io.raveerocks.gdgfinder.util

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import io.raveerocks.gdgfinder.adapter.GdgListAdapter
import io.raveerocks.gdgfinder.network.GdgChapter

@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<GdgChapter>?) {
    val adapter = recyclerView.adapter as GdgListAdapter
    adapter.submitList(data) {
        recyclerView.scrollToPosition(0)
    }
}