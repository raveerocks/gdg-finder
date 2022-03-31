package io.raveerocks.gdgfinder.adapter

import io.raveerocks.gdgfinder.network.GdgChapter

class GdgClickListener(val clickListener: (chapter: GdgChapter) -> Unit) {
    fun onClick(chapter: GdgChapter) = clickListener(chapter)
}