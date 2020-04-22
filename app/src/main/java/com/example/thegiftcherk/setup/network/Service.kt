package com.example.thegiftcherk.setup.network

import com.example.thegiftcherk.features.ui.friends.Friend
import com.example.thegiftcherk.features.ui.login.models.SendNewWish
import com.example.thegiftcherk.features.ui.login.models.SendUser
import com.example.thegiftcherk.features.ui.login.models.SendUserRegister
import com.example.thegiftcherk.features.ui.login.models.User
import com.example.thegiftcherk.features.ui.search.models.Item
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*


interface Service {
    //region User
    @POST("user/login")
    suspend fun login(
        @Body sendUser: SendUser
    ): Response<User>

    @FormUrlEncoded
    @POST("user/reset_password")
    suspend fun rememberPass(
        @Field("userMail") email: String
    ): Response<Operation>

    @POST("user/register")
    suspend fun register(
        @Body sendUserRegister: SendUserRegister
    ): Response<Any>
    
    @GET("wishes/")
    suspend fun getOwnWishes(
    ): Response<List<Item>>

    @POST("wishes/")
    suspend fun addNewWish(
        @Body sendNewWish: SendNewWish
    ): Response<Operation>

    @GET("friends")
    suspend fun getFriends(
    ): Response<List<Friend>>


    @Streaming
    @Multipart
    @POST("/api/upload/picture/upload_pics")
    fun uploadImage(
        @Part("entity_id") entityId: RequestBody?,
        @Part("picType") picType: RequestBody?,
        @Part data: MultipartBody.Part?
    ): Response<Any>

    //endregion Others
}