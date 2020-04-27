package com.example.thegiftcherk.features.ui.login.models

import android.os.Parcelable
import android.telephony.cdma.CdmaCellLocation
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ListaPeticionesAmistad(
    @SerializedName("id") val requestId: String?,
    @SerializedName("userId") val userId: String?,
    @SerializedName("friendRequestId") val friendRequestId: String?
) : Parcelable
