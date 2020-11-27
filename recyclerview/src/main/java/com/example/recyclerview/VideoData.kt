package com.example.recyclerview

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class VideoData(val name: String, val desc: String) : Parcelable