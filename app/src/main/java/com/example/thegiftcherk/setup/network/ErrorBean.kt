package com.example.thegiftcherk.setup.network

import com.google.gson.annotations.SerializedName

data class ErrorBean(
    val localiced: Boolean,
    val message: String,
    @SerializedName("message_dev")
    val messageDev: String,
    val type: Int
)