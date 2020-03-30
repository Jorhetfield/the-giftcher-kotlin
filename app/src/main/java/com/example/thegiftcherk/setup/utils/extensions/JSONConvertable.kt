package com.example.thegiftcherk.setup.utils.extensions

import com.google.gson.Gson

interface JSONConvertable {
    fun toJSON(): String = Gson().toJson(this)

    companion object {
        inline fun <reified T : JSONConvertable> String.toObject(): T = Gson().fromJson(this, T::class.java)
    }
}


