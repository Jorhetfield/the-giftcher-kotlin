package com.example.thegiftcherk.setup.network

import com.example.thegiftcherk.features.ui.friends.Friend
import com.example.thegiftcherk.features.ui.login.models.*
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

    @PUT("user/update_password")
    suspend fun changePassword(
        @Body sendEditPassword: SendEditPassword
    ): Response<Operation>

    @POST("user/register")
    suspend fun register(
        @Body sendUserRegister: SendUserRegister
    ): Response<Any>
    
    @GET("wishes/")
    suspend fun getOwnWishes(
    ): Response<List<Item>>

    @GET("/wishes/all_wishes")
    suspend fun getAllWishes(
    ): Response<List<Item>>


    @GET("wishes/categories/{categoryId}")
    suspend fun getWishesByCategories(
        @Path("categoryId") categoryId : String
    ): Response<List<Item>>

    @PUT("wishes/{wishId}")
    suspend fun editWish(
        @Path("wishId") wishId : String,
        @Body sendEditWish: SendEditWish
    ): Response<Operation>

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
    @POST("/user/google_cloud_image")
    suspend fun uploadImage(
        @Part file: MultipartBody.Part?
    ): Response<Any>

    @GET("user/get_profile_image")
    suspend fun getProfileImage(
    ): Response<File>

    @GET("/wishes/wish_image/{id}")
    suspend fun getWisheImage(
        @Path("id") id:String
    ): Response<File>

    @DELETE("/wishes/{id}")
    suspend fun deleteWish(
        @Path("id") id:String
    ): Response<Operation>

    @GET("/wishes/userId/{userId}")
    suspend fun getFriendWishes(
        @Path("userId") userId:String
    ): Response<List<Item>>

    @PUT("user/update/")
    suspend fun editProfile(
        @Body sendEditUser: SendEditUser
    ): Response<Operation>

    @POST("wishes/copy/userId/{userId}/id/{wishId}")
    suspend fun copyWishFromUser(
        @Path("userId") userId:String,
        @Path("wishId") wishId:String
    ): Response<Operation>
    //endregion Others
}