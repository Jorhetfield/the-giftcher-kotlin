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
import java.io.File
import java.util.stream.Stream


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

    @GET("user/get_users")
    suspend fun getAllUsers(
    ): Response<List<Friend>>

    @Multipart
    @POST("/api/upload/picture/upload_pics")
    suspend fun uploadImage(
        @Part file: MultipartBody.Part?
    ): Response<Any>

    @GET("user/get_profile_image")
    suspend fun getProfileImage(
    ): Response<File>

    @GET("/wishes/wish_image/{{id}}")
    suspend fun getWisheImage(
        @Path("id") id:String
    ): Response<File>

    //endregion Others
}