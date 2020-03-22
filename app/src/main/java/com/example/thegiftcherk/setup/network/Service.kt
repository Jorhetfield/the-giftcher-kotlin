package com.example.thegiftcherk.setup.network

import com.example.thegiftcherk.features.ui.friends.Friend
import com.example.thegiftcherk.features.ui.login.models.User
import com.example.thegiftcherk.features.ui.search.models.Item
import retrofit2.Response
import retrofit2.http.*


interface Service {
    //region User
    @FormUrlEncoded
    @POST("/api/Login/login_drivers") // TODO change Url
    suspend fun login(
        @Field("email") email: String,
        @Field("password") pass: String
    ): Response<User>

    @FormUrlEncoded
    @POST("/api/login/remember_password") // TODO change Url
    suspend fun rememberPass(
        @Field("email") email: String
    ): Response<Operation>

    @FormUrlEncoded
    @POST("/api/register/register_client") // TODO change Url
    suspend fun register(
        @Field("email") email: String,
        @Field("password") pass: String,
        @Field("name") name: String,
        @Field("number") phone: String
    ): Response<User>


    @GET("/api/invoice/amount_of_month") // TODO change Url
    suspend fun getItems(
    ): Response<List<Item>>

    @GET("/api/invoice/amount_of_month") // TODO change Url
    suspend fun getFriends(
    ): Response<List<Friend>>

    //endregion Others
}