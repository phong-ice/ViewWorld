package com.example.watchworld.data

import android.os.Parcelable
import androidx.annotation.NonNull
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Picture(
    val id: String,
    val tags: String,
    val url_m: String,
    val height_m: Int,
    val width_m: Int,
    val url_o: String,
    val height_o: Int,
    val width_o: Int
) : Parcelable