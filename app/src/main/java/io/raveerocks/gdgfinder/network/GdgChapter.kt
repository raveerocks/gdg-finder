package io.raveerocks.gdgfinder.network

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize


@Parcelize
data class GdgChapter(
    @Json(name = "chapter_name") val name: String,
    @Json(name = "cityarea") val city: String,
    val country: String,
    val region: String,
    val website: String,
    val geo: LatLong
) : Parcelable



