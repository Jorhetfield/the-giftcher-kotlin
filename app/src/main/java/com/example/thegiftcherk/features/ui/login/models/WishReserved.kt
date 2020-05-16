package com.example.thegiftcherk.features.ui.login.models

import android.content.ClipData
import android.os.Parcelable
import android.telephony.cdma.CdmaCellLocation
import com.example.thegiftcherk.features.ui.search.models.Item
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class WishReserved(
    @SerializedName("id") val id: String?,
    @SerializedName("userId") val userId:String?,
    @SerializedName("wishId") val wishId: String?,
    @SerializedName("friendId") val friendId: String?
) : Parcelable
