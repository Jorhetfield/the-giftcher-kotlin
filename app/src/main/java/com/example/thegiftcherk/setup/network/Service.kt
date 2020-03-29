package com.example.thegiftcherk.setup.network

import android.media.Image
import android.net.Uri
import com.example.thegiftcherk.features.ui.friends.Friend
import com.example.thegiftcherk.features.ui.login.models.SendUser
import com.example.thegiftcherk.features.ui.login.models.User
import com.example.thegiftcherk.features.ui.search.models.Item
import retrofit2.Response
import retrofit2.http.*


interface Service {
    //region User
    @POST("/api/Login/login_drivers") // TODO change Url
    suspend fun login(
        @Body sendUser: SendUser
    ): Response<User>

    @FormUrlEncoded
    @POST("/api/login/remember_password") // TODO change Url
    suspend fun rememberPass(
        @Field("email") email: String
    ): Response<Operation>

    @FormUrlEncoded
    @POST("/api/register/register_client") // TODO change Url
    suspend fun register(
        @Field("name") name: String,
        @Field("surname") surname: String,
        @Field("email") email: String,
        @Field("username") username: String,
        @Field("password") pass: String,
        @Field("date_of_birth") birthDay: String
    ): Response<Any>


    @GET("/api/invoice/amount_of_month") // TODO change Url
    suspend fun getItems(
    ): Response<List<Item>>

    @GET("/api/invoice/amount_of_month") // TODO change Url
    suspend fun getFriends(
    ): Response<List<Friend>>

    @POST("/api/invoice/amount_of_month") // TODO change Url
    suspend fun uploadImage(
        @Part ("image") image: Uri
    ): Response<Any>

    //endregion Others
}