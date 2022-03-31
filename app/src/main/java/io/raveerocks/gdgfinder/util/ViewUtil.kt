package io.raveerocks.gdgfinder.util

import android.view.View
import androidx.databinding.BindingAdapter
import io.raveerocks.gdgfinder.network.GdgChapter

@BindingAdapter("showOnlyWhenEmpty")
fun View.showOnlyWhenEmpty(data: List<GdgChapter>?) {
    visibility = when {
        data == null || data.isEmpty() -> View.VISIBLE
        else -> View.GONE
    }
}