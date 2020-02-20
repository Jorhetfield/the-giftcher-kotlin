package com.example.thegiftcherk.setup.network


import android.content.Context
import com.example.thegiftcherk.BuildConfig
import com.example.thegiftcherk.R
import com.example.thegiftcherk.features.ui.login.models.User
import com.example.thegiftcherk.features.ui.search.models.Item
import com.example.thegiftcherk.setup.MOCK_DELAY
import com.example.thegiftcherk.setup.network.NetworkExceptionController.checkException
import com.example.thegiftcherk.setup.network.NetworkExceptionController.checkResponse
import com.example.thegiftcherk.setup.utils.extensions.getJsonFromResource
import com.example.thegiftcherk.setup.utils.extensions.getMockResponseResult
import com.google.gson.Gson
import kotlinx.coroutines.delay

class Repository(private val service: Service, private val context: Context) {
    //region User
    suspend fun doLogin(
        email: String, pass: String,
        fake: Boolean = BuildConfig.MOCK
    ): ResponseResult<User> {
        return if (!fake) {
            try {
                val response = service.login(email, pass)
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
        email: String, pass: String, name: String, phone: String,
        fake: Boolean = BuildConfig.MOCK
    ): ResponseResult<User> {
        return if (!fake) {
            try {
                val response = service.register(email, pass, name, phone)
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
                val response = service.getItems()
                checkResponse(context, response)

            } catch (e: Exception) {
                checkException(context, e)
            }
        } else {
            delay(MOCK_DELAY)
            val json = context.getJsonFromResource(R.raw.items)
            val response: List<Item> =
                Gson().fromJson(json, Array<Item>::class.java).toList()
            ResponseResult.Success(response)
        }
    }

    //endregion Others
}

