package io.raveerocks.gdgfinder.network

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Parcelize
data class Filter(
    @Json(name = "region") val regions: List<String>
) : Parcelable
