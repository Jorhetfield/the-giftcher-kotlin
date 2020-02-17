package com.example.thegiftcherk.setup.network

sealed class ResponseResult<out T : Any> {
    data class Success<out T : Any>(val value: T) : ResponseResult<T>()
    data class NotContent(val message: String) : ResponseResult<Nothing>()
    data class Empty(val message: String) : ResponseResult<Nothing>()
    data class Error(val message: String, val cause: Exception? = null) : ResponseResult<Nothing>()
    data class Forbidden(val message: String) : ResponseResult<Nothing>()
}