package com.example.thegiftcherk.setup

import android.content.Context
import android.content.SharedPreferences
import com.example.thegiftcherk.BuildConfig

class Prefs(context: Context) {
    //region Vars
    private val FILENAME = "${BuildConfig.APPLICATION_ID}.prefs"
    private val TOKEN = "TOKEN"
    private val FCM_TOKEN = "FCM_TOKEN"
    private val USER = "USER"
    private val DARK_MODE = "DARK_MODE"
    private val FILTER = "FILTER"
    private val STATE = "STATE"
    private val USER_ID = "USER_ID"
    private val USER_TYPE = "USER_TYPE"
    private val AVAILABLE = "AVAILABLE"
    private val SCREEN_WIDTH = "WIDTH"
    private val SCREEN_HEIGHT = "HEIGHT"
    private val NOTIFICATIONS_GENERAL_ENABLED = "NOTIFICATIONS_GENERAL_ENABLED"
    private val SERVICES_APPLIED = "SERVICES_APPLIED"
    private val LIKES = "LIKES"
    private val FRIEND_ID = "FRIEND_ID"
    private val CHECK_IN_ENABLED = "CHECK_IN_ENABLED"
    private val SERVICE_ID = "SERVICE_ID "
    private val FIRST_LOGIN = "FIRST_LOGIN"
    private val OBS_LOCATION_ADDRESS = "OBS_LOCATION_ADDRESS"

    private val LAST_LOCATION_LAT = "LAST_LOCATION_LAT"
    private val LAST_LOCATION_LNG = "LAST_LOCATION_LNG"


    //endregion

    private val prefs: SharedPreferences = context.getSharedPreferences(FILENAME, Context.MODE_PRIVATE)

    //region UserPrefs
    var token: String?
        get() = prefs.getString(TOKEN, "")
        set(value) = prefs.edit().putString(TOKEN, value).apply()

    var fcmToken: String?
        get() = prefs.getString(FCM_TOKEN, "")
        set(value) = prefs.edit().putString(FCM_TOKEN, value).apply()

    var likes: String?
        get() = prefs.getString(LIKES, "")
        set(value) = prefs.edit().putString(LIKES, value).apply()

    var friendId: String?
        get() = prefs.getString(FRIEND_ID, "")
        set(value) = prefs.edit().putString(FRIEND_ID, value).apply()

    var userType: String?
        get() = prefs.getString(USER_TYPE, "")
        set(value) = prefs.edit().putString(USER_TYPE, value).apply()
    var userId: String?
        get() = prefs.getString(USER_ID, "")
        set(value) = prefs.edit().putString(USER_ID, value).apply()
    var user: String?
        get() = prefs.getString(USER, "")
        set(value) = prefs.edit().putString(USER, value).apply()

    var filter: String?
        get() = prefs.getString(FILTER, "time")
        set(value) = prefs.edit().putString(FILTER, value).apply()

    var firstLogin: Boolean
        get() = prefs.getBoolean(FIRST_LOGIN, true)
        set(value) = prefs.edit().putBoolean(FIRST_LOGIN, value).apply()

    var state: String?
        get() = prefs.getString(STATE, "available")
        set(value) = prefs.edit().putString(STATE, value).apply()

    var screenWidth: String?
        get() = prefs.getString(SCREEN_WIDTH, "")
        set(value) = prefs.edit().putString(SCREEN_WIDTH, value).apply()

    var screenHeight: String?
        get() = prefs.getString(SCREEN_HEIGHT, "")
        set(value) = prefs.edit().putString(SCREEN_HEIGHT, value).apply()

    var lastLat: String?
        get() = prefs.getString(LAST_LOCATION_LAT, "")
        set(value) = prefs.edit().putString(LAST_LOCATION_LAT, value).apply()

    var lastLng: String?
        get() = prefs.getString(LAST_LOCATION_LNG, "")
        set(value) = prefs.edit().putString(LAST_LOCATION_LNG, value).apply()

    var darkMode: Boolean
        get() = prefs.getBoolean(DARK_MODE, false)
        set(value) = prefs.edit().putBoolean(DARK_MODE, value).apply()

    var isAvailable: Boolean
        get() = prefs.getBoolean(AVAILABLE, true)
        set(value) = prefs.edit().putBoolean(AVAILABLE, value).apply()
    //endregion

    //region RequestPrefs
    var servicesApplied: String?
        get() = prefs.getString(SERVICES_APPLIED, "[{}]")
        set(value) = prefs.edit().putString(SERVICES_APPLIED, value).apply()
    //endregion

    //region OtherPrefs
    var notificationsEnabled: Boolean
        get() = prefs.getBoolean(NOTIFICATIONS_GENERAL_ENABLED, true)
        set(value) = prefs.edit().putBoolean(NOTIFICATIONS_GENERAL_ENABLED, value).apply()
    var checkIn: Boolean
        get() = prefs.getBoolean(CHECK_IN_ENABLED, false)
        set(value) = prefs.edit().putBoolean(CHECK_IN_ENABLED, value).apply()
    var serviceId: Int
        get() = prefs.getInt(SERVICE_ID, 0)
        set(value) = prefs.edit().putInt(SERVICE_ID, value).apply()
    var obsLocationAddress: String?
        get() = prefs.getString(OBS_LOCATION_ADDRESS, "OBS Motorpool Center")
        set(value) = prefs.edit().putString(OBS_LOCATION_ADDRESS, value).apply()
    //endregion

    //region Clear and remove Prefs
    fun clear(): Boolean {
        return prefs.edit().clear().commit()
    }

    fun clearServicesApplied() {
        remove(SERVICES_APPLIED)
    }

    private fun remove(key: String) {
        prefs.edit().remove(key).apply()
    }

    fun clearLogin() {
        remove(TOKEN)
        remove(USER)
        remove(USER_TYPE)
    }
    //endregion

}