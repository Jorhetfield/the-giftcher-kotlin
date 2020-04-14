package com.example.thegiftcherk.setup.network


import android.content.Context
import com.example.thegiftcherk.BuildConfig
import com.example.thegiftcherk.R
import com.example.thegiftcherk.features.ui.friends.Friend
import com.example.thegiftcherk.features.ui.login.models.SendUser
import com.example.thegiftcherk.features.ui.login.models.SendUserRegister
import com.example.thegiftcherk.features.ui.login.models.User
import com.example.thegiftcherk.features.ui.search.models.Item
import com.example.thegiftcherk.setup.MOCK_DELAY
import com.example.thegiftcherk.setup.network.NetworkExceptionController.checkException
import com.example.thegiftcherk.setup.network.NetworkExceptionController.checkResponse
import com.example.thegiftcherk.setup.utils.extensions.getJsonFromResource
import com.example.thegiftcherk.setup.utils.extensions.getMockResponseResult
import com.google.gson.Gson
import kotlinx.coroutines.delay
import okhttp3.MultipartBody
import okhttp3.RequestBody

class Repository(private val service: Service, private val context: Context) {
    //region User
    suspend fun doLogin(
        sendUser: SendUser,
        fake: Boolean = BuildConfig.MOCK
    ): ResponseResult<User> {
        return if (!fake) {
            try {
                val response = service.login(sendUser)
                checkResponse(context, response)
            } catch (e: Exception) {
                checkException(context, e)
            }
        } else {
            delay(MOCK_DELAY)
            context.getMockResponseResult(R.raw.user)
        }
    }

    suspend fun rememberPass(
        email: String,
        fake: Boolean = BuildConfig.MOCK
    ): ResponseResult<Operation> {
        return if (!fake) {
            try {
                val response = service.rememberPass(email)
                checkResponse(context, response)
            } catch (e: Exception) {
                checkException(context, e)
            }
        } else {
            context.getMockResponseResult(R.raw.user)
        }
    }

    suspend fun doRegister(
        sendUserRegister: SendUserRegister,
        fake: Boolean = BuildConfig.MOCK
    ): ResponseResult<Any> {
        return if (!fake) {
            try {
                val response = service.register(sendUserRegister)
                checkResponse(context, response)
            } catch (e: Exception) {
                checkException(context, e)
            }
        } else {
            delay(MOCK_DELAY)
            context.getMockResponseResult(R.raw.user)
        }
    }

    suspend fun getItems(
        fake: Boolean = BuildConfig.MOCK
    ): ResponseResult<List<Item>> {
        return if (!fake) {
            try {
                val response = service.getOwnWishes()
                checkResponse(context, response)

            } catch (e: Exception) {
                checkException(context, e)
            }
        } else {
            delay(MOCK_DELAY)
            val json = context.getJsonFromResource(R.raw.lista_personal)
            val response: List<Item> =
                Gson().fromJson(json, Array<Item>::class.java).toList()
            ResponseResult.Success(response)
        }
    }

    suspend fun getFriends(
        fake: Boolean = BuildConfig.MOCK
    ): ResponseResult<List<Friend>> {
        return if (!fake) {
            try {
                val response = service.getFriends()
                checkResponse(context, response)

            } catch (e: Exception) {
                checkException(context, e)
            }
        } else {
            delay(MOCK_DELAY)
            val json = context.getJsonFromResource(R.raw.friends)
            val response: List<Friend> =
                Gson().fromJson(json, Array<Friend>::class.java).toList()
            ResponseResult.Success(response)
        }
    }


    suspend fun uploadImage(
        entityId:RequestBody?,
        picType:RequestBody?,
        data:MultipartBody.Part?,
        fake: Boolean = BuildConfig.MOCK
    ): ResponseResult<Any> {
        return if (!fake) {
            try {
                val response = service.uploadImage(entityId, picType, data)
                checkResponse(context, response)

            } catch (e: Exception) {
                checkException(context, e)
            }
        } else {
            delay(MOCK_DELAY)
            val json = context.getJsonFromResource(R.raw.lista_personal)
            val response: List<Item> =
                Gson().fromJson(json, Array<Item>::class.java).toList()
            ResponseResult.Success(response)
        }
    }

    //endregion Others
}


