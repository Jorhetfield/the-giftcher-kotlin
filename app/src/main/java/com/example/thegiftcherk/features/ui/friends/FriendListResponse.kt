package com.example.thegiftcherk.features.ui.friends

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FriendListResponse(
    @SerializedName("friends")val friends: List<Friend>?
) : Parcelable
