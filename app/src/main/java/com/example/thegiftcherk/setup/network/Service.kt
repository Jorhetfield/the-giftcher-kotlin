package com.example.thegiftcherk.setup.network

import android.media.Image
import android.net.Uri
import com.example.thegiftcherk.features.ui.friends.Friend
import com.example.thegiftcherk.features.ui.login.models.SendUser
import com.example.thegiftcherk.features.ui.login.models.SendUserRegister
import com.example.thegiftcherk.features.ui.login.models.User
import com.example.thegiftcherk.features.ui.search.models.Item
import com.google.android.material.textfield.TextInputLayout
import retrofit2.Response
import retrofit2.http.*
import java.io.File


interface Service {
    //region User
    @POST("user/login") // TODO change Url
    suspend fun login(
        @Body sendUser: SendUser
    ): Response<User>

    @FormUrlEncoded
    @POST("/api/login/remember_password") // TODO change Url
    suspend fun rememberPass(
        @Field("email") email: String
    ): Response<Operation>

    @POST("user/register") // TODO change Url
    suspend fun register(
        @Body sendUserRegister: SendUserRegister
    ): Response<Any>
    
    @GET("/api/invoice/amount_of_month") // TODO change Url
    suspend fun getItems(
    ): Response<List<Item>>

    @GET("/api/invoice/amount_of_month") // TODO change Url
    suspend fun getFriends(
    ): Response<List<Friend>>

    @POST("/api/invoice/amount_of_month") // TODO change Url
    suspend fun uploadImage(
        @Part ("image") image: File
    ): Response<Any>

    //endregion Others
}