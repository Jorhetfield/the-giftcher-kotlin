package com.example.thegiftcherk.setup.utils.extensions

import android.app.Notification
import android.app.NotificationManager
import android.content.Context
import android.net.ConnectivityManager
import android.os.PowerManager
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.google.gson.Gson
import com.example.thegiftcherk.setup.network.ResponseResult
import retrofit2.Response

fun Context.getJsonFromResource(res: Int): String {
    return resources.openRawResource(res)
        .bufferedReader().use { it.readText() }
}

inline fun <reified T : Any> T.json(): String = Gson().toJson(this, T::class.java)
inline fun <reified T : Any> String.fromJson(): T = Gson().fromJson(this, T::class.java)

inline fun <reified T : Any> Context.getMockListResponse(raw: Int): Response<List<T>> {
    val json = this.getJsonFromResource(raw)
    val response: List<T> = Gson().fromJson(json, Array<T>::class.java).toList()
    return retrofit2.Response.success(response)
}

inline fun <reified T : Any> Context.getMockResponse(raw: Int): Response<T> {
    val json = this.getJsonFromResource(raw)
    val response: T = json.fromJson()
    return retrofit2.Response.success(response)
}

inline fun <reified T : Any> Context.getMockResult(raw: Int): T {
    val json = this.getJsonFromResource(raw)
    return json.fromJson()
}

inline fun <reified T : Any> Context.getMockResponseResult(raw: Int): ResponseResult.Success<T> {
    return ResponseResult.Success(this.getMockResult(raw))
}

inline fun Context.notification(channelId: String, func: NotificationCompat.Builder.() -> Unit): Notification {
    val builder = NotificationCompat.Builder(this, channelId)
    builder.func()
    return builder.build()
}

val Context.notificationManager: NotificationManager
    get() = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

val Context.connectivityManager: ConnectivityManager
    get() = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

val Context.powerManager: PowerManager
    get() = getSystemService(Context.POWER_SERVICE) as PowerManager

fun Context.color(color: Int) = ContextCompat.getColor(this, color)

fun Context.toastShort(message: String) = Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

fun Context.toastLong(message: String) = Toast.makeText(this, message, Toast.LENGTH_LONG).show()