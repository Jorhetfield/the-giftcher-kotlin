package com.example.thegiftcherk.features.ui.friends

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Friend(
    @SerializedName("id")val id: String?,
    @SerializedName("friendId")val friendId: String?,
    @SerializedName("username")val username: String?,
    @SerializedName("name")val name: String?,
    @SerializedName("lastName")val lastName: String?,
    @SerializedName("mail")val mail: String?,
    @SerializedName("birthday")val birthday: String?,
    @SerializedName("imageName")val imageName: String?,
    @SerializedName("imagePath")val imagePath: String?
) : Parcelable
