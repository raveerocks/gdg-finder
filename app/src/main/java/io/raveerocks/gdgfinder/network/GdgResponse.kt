package io.raveerocks.gdgfinder.network

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Parcelize
data class GdgResponse(
    @Json(name = "filters_") val filters: Filter,
    @Json(name = "data") val chapters: List<GdgChapter>
) : Parcelable
