package com.example.thegiftcherk.features.ui.login.models

import android.content.ClipData
import android.os.Parcelable
import android.telephony.cdma.CdmaCellLocation
import com.example.thegiftcherk.features.ui.search.models.Item
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ReservedWishesList(
    @SerializedName("reservedWishes") val reservedWishes: List<ReservedWishes>?
) : Parcelable
