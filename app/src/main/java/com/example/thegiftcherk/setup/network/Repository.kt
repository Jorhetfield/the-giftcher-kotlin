package com.example.thegiftcherk.setup.network


import android.content.Context
import com.example.thegiftcherk.BuildConfig
import com.example.thegiftcherk.R
import com.example.thegiftcherk.features.ui.friends.Friend
import com.example.thegiftcherk.features.ui.login.models.*
import com.example.thegiftcherk.features.ui.search.models.Item
import com.example.thegiftcherk.setup.MOCK_DELAY
import com.example.thegiftcherk.setup.network.NetworkExceptionController.checkException
import com.example.thegiftcherk.setup.network.NetworkExceptionController.checkResponse
import com.example.thegiftcherk.setup.utils.extensions.getJsonFromResource
import com.example.thegiftcherk.setup.utils.extensions.getMockResponseResult
import com.google.gson.Gson
import kotlinx.coroutines.delay
import okhttp3.MultipartBody
import java.io.File

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

    suspend fun getSingleUser(
        userId: String,
        fake: Boolean = BuildConfig.MOCK
    ): ResponseResult<Friend> {
        return if (!fake) {
            try {
                val response = service.getSingleUser(userId)
                checkResponse(context, response)
            } catch (e: Exception) {
                checkException(context, e)
            }
        } else {
            delay(MOCK_DELAY)
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

    suspend fun getAllWishes(
        fake: Boolean = BuildConfig.MOCK
    ): ResponseResult<List<Item>> {
        return if (!fake) {
            try {
                val response = service.getAllWishes()
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

    suspend fun getWishesByCategories(
        categoryId: String,
        fake: Boolean = BuildConfig.MOCK
    ): ResponseResult<List<Item>> {
        return if (!fake) {
            try {
                val response = service.getWishesByCategories(categoryId)
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

    suspend fun editWish(
        wishId: String,
        sendEditWish: SendEditWish,
        fake: Boolean = BuildConfig.MOCK
    ): ResponseResult<Operation> {
        return if (!fake) {
            try {
                val response = service.editWish(wishId, sendEditWish)
                checkResponse(context, response)

            } catch (e: Exception) {
                checkException(context, e)
            }
        } else {
            delay(MOCK_DELAY)
            context.getMockResponseResult(R.raw.user)
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

    suspend fun getAllUsers(
        fake: Boolean = BuildConfig.MOCK
    ): ResponseResult<List<Friend>> {
        return if (!fake) {
            try {
                val response = service.getAllUsers()
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
        file: MultipartBody.Part?,
        fake: Boolean = BuildConfig.MOCK
    ): ResponseResult<Any> {
        return if (!fake) {
            try {
                val response = service.uploadImage(file)
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

    suspend fun addWish(
        sendNewWish: SendNewWish,
        fake: Boolean = BuildConfig.MOCK
    ): ResponseResult<Operation> {
        return if (!fake) {
            try {
                val response = service.addNewWish(sendNewWish)
                checkResponse(context, response)

            } catch (e: Exception) {
                checkException(context, e)
            }
        } else {
            delay(MOCK_DELAY)
            context.getMockResponseResult(R.raw.user)
        }
    }

    suspend fun getProfileImage(
        fake: Boolean = BuildConfig.MOCK
    ): ResponseResult<File> {
        return if (!fake) {
            try {
                val response = service.getProfileImage()
                checkResponse(context, response)

            } catch (e: Exception) {
                checkException(context, e)
            }
        } else {
            delay(MOCK_DELAY)
            context.getMockResponseResult(R.raw.user)
        }
    }

    suspend fun getWishImage(
        id: String,
        fake: Boolean = BuildConfig.MOCK
    ): ResponseResult<File> {
        return if (!fake) {
            try {
                val response = service.getWisheImage(id)
                checkResponse(context, response)

            } catch (e: Exception) {
                checkException(context, e)
            }
        } else {
            delay(MOCK_DELAY)
            context.getMockResponseResult(R.raw.user)
        }
    }

    suspend fun deleteWish(
        id: String,
        fake: Boolean = BuildConfig.MOCK
    ): ResponseResult<Operation> {
        return if (!fake) {
            try {
                val response = service.deleteWish(id)
                checkResponse(context, response)

            } catch (e: Exception) {
                checkException(context, e)
            }
        } else {
            delay(MOCK_DELAY)
            context.getMockResponseResult(R.raw.user)
        }
    }

    suspend fun getFriendWishes(
        id: String,
        fake: Boolean = BuildConfig.MOCK
    ): ResponseResult<List<Item>> {
        return if (!fake) {
            try {
                val response = service.getFriendWishes(id)
                checkResponse(context, response)

            } catch (e: Exception) {
                checkException(context, e)
            }
        } else {
            delay(MOCK_DELAY)
            context.getMockResponseResult(R.raw.user)
        }
    }

    suspend fun editUser(
        sendEditUser: SendEditUser,
        fake: Boolean = BuildConfig.MOCK
    ): ResponseResult<Operation> {
        return if (!fake) {
            try {
                val response = service.editProfile(sendEditUser)
                checkResponse(context, response)

            } catch (e: Exception) {
                checkException(context, e)
            }
        } else {
            delay(MOCK_DELAY)
            context.getMockResponseResult(R.raw.user)
        }
    }

    suspend fun changePassword(
        sendEditPassword: SendEditPassword,
        fake: Boolean = BuildConfig.MOCK
    ): ResponseResult<Operation> {
        return if (!fake) {
            try {
                val response = service.changePassword(sendEditPassword)
                checkResponse(context, response)

            } catch (e: Exception) {
                checkException(context, e)
            }
        } else {
            delay(MOCK_DELAY)
            context.getMockResponseResult(R.raw.user)
        }
    }

    suspend fun copyWishFromUser(
        userId: String,
        wishId: String,
        fake: Boolean = BuildConfig.MOCK
    ): ResponseResult<Operation> {
        return if (!fake) {
            try {
                val response = service.copyWishFromUser(userId, wishId)
                checkResponse(context, response)

            } catch (e: Exception) {
                checkException(context, e)
            }
        } else {
            delay(MOCK_DELAY)
            context.getMockResponseResult(R.raw.user)
        }
    }

    suspend fun getFriendRequests(
        fake: Boolean = BuildConfig.MOCK
    ): ResponseResult<List<ListaPeticionesAmistad>> {
        return if (!fake) {
            try {
                val response = service.getFriendRequests()
                checkResponse(context, response)

            } catch (e: Exception) {
                checkException(context, e)
            }

        } else {
            delay(MOCK_DELAY)
            context.getMockResponseResult(R.raw.chip_response)
        }
    }

    suspend fun confirmFriend(
        friendRequestId: String,
        fake: Boolean = BuildConfig.MOCK
    ): ResponseResult<Operation> {
        return if (!fake) {
            try {
                val response = service.confirmFriend(friendRequestId)
                checkResponse(context, response)

            } catch (e: Exception) {
                checkException(context, e)
            }

        } else {
            delay(MOCK_DELAY)
            context.getMockResponseResult(R.raw.user)
        }
    }

    suspend fun deleteFriendRequest(
        friendRequestId: String,
        fake: Boolean = BuildConfig.MOCK
    ): ResponseResult<Operation> {
        return if (!fake) {
            try {
                val response = service.deleteFriendRequest(friendRequestId)
                checkResponse(context, response)

            } catch (e: Exception) {
                checkException(context, e)
            }

        } else {
            delay(MOCK_DELAY)
            context.getMockResponseResult(R.raw.user)
        }
    }

    suspend fun deleteFriend(
        friendId: String,
        fake: Boolean = BuildConfig.MOCK
    ): ResponseResult<List<ListaPeticionesAmistad>> {
        return if (!fake) {
            try {
                val response = service.deleteFriend(friendId)
                checkResponse(context, response)

            } catch (e: Exception) {
                checkException(context, e)
            }

        } else {
            delay(MOCK_DELAY)
            context.getMockResponseResult(R.raw.user)
        }
    }

    suspend fun createFriendRequest(
        friendRequestId: Operation,
        fake: Boolean = BuildConfig.MOCK
    ): ResponseResult<Operation> {
        return if (!fake) {
            try {
                val response = service.createFriendRequest(friendRequestId)
                checkResponse(context, response)

            } catch (e: Exception) {
                checkException(context, e)
            }

        } else {
            delay(MOCK_DELAY)
            context.getMockResponseResult(R.raw.user)
        }
    }




    //endregion Others
}


