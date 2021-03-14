package com.example.watchworld.data

import android.graphics.Picture
import android.os.Parcelable

data class Photos(
    var page: Int,
    var pages: Int,
    var perpages: Int,
    var total: Int,
    var photo: List<com.example.watchworld.data.Picture>
)