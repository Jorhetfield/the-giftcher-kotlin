package com.example.thegiftcherk.setup.network

import com.example.thegiftcherk.features.ui.search.models.Item
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface Service {
    //region User
//    @FormUrlEncoded
//    @POST("/api/Login/login_drivers")
//    suspend fun login(
//        @Field("email") email: String,
//        @Field("password") pass: String,
//        @Field("iid_token") FCMToken: String?
//    ): Response<User>
//
//    @FormUrlEncoded
//    @POST("/api/login/remember_password")
//    suspend fun rememberPass(
//        @Field("email") email: String
//    ): Response<Operation>
//
//    @FormUrlEncoded
//    @POST("/api/driver/change_state")
//    suspend fun changeState(
//        @Field("token") token: String
//    ): Response<State>
//
//    @FormUrlEncoded
//    @POST("/api/driver/notify_client")
//    suspend fun notifyCustomer(
//        @Field("service_id") serviceId: String?
//    ): Response<Any>
//
//    @FormUrlEncoded
//    @GET("/api/driver/state")
//    suspend fun getState(
//        @Field("token") token: String
//    ): Response<State>
//
//    @FormUrlEncoded
//    @GET("/api/driver/state")
//    suspend fun getAmountOfDay(): Response<String>
//
//    @FormUrlEncoded
//    @GET("/api/driver/state")
//    suspend fun getAmountOfMonth(
//        @Field("current_month") CurrentMonth: String
//    ): Response<String>
//
//    @GET("/api/invoice/amount_of_day")
//    suspend fun getDailySum(
//    ): Response<Amount>
//
    @GET("/api/invoice/amount_of_month") // TODO change Url
    suspend fun getItems(
    ): Response<List<Item>>
    //
//    @GET("/api/invoice/invoice_of_day")
//    suspend fun getDailyInvoices(
//    ): Response<List<InvoiceResponse>>
//
//    @GET("/api/invoice/invoice_of_month")
//    suspend fun getMonthlyInvoices(
//        @Query("month") CurrentMonth: Int?
//    ): Response<List<InvoiceResponse>>
//
//    @GET("/api/invoice/invoice_pdf")
//    suspend fun getInvoicePdf(
//        @Query("pdf") pdf: String?
//    ): Response<Any>
//
//
//    @GET("/api/user/bookings")
//    suspend fun getBookings(
//        @Query("page") page: Int
//    ): Response<List<BookingResponse>>
//
//    @GET("/api/user/payments")
//    suspend fun getPayments(): Response<List<PaymentResponse>>
//    //endregion User
//
//    //region Map
//    @GET("/api/maps/route")
//    suspend fun getRoute(
//        @Query("start_address") startAddress: String?,
//        @Query("end_address") endAddress: String?
//    ): Response<RouteResponse>
//
//    @GET("/api/map/offers")
//    suspend fun getOffers(): Response<List<OfferResponse>>
//
//    @GET("/api/driver/services_for_driver")
//    suspend fun getServicesNotAssigned(
//        @Query("lat") lat: Double?,
//        @Query("lng") lng: Double?,
//        @Query("page") page: Int,
//        @Query("limit") limit: Int,
//        @Query("order_by") orderBy: String
//
//    ): Response<List<ServiceResponse>>
//
//    @FormUrlEncoded
//    @POST("/api/driver/change_state_service")
//    suspend fun assignService(
//        @Field("state") state: Int?,
//        @Field("service_id") id: String?,
//        @Field("price_taximeter") priceTaximeter: Float
//    ): Response<Any>
//
//    //region Others
//    @GET("/api/autocomplete/places")
//    suspend fun getPlaces(
//        @Query("input") input: String?,
//        @Query("google_api_key") googleApiKey: String?
//    ): Response<List<Place>>
//
//    @GET("/api/message/messages")
//    suspend fun getMessages(): Response<List<MessageResponse>>
//
//    @FormUrlEncoded
//    @POST("/api/message/message")
//    suspend fun sendMessage(
//        @Field("title") title: String,
//        @Field("text") text: String
//    ): Response<Any>
//
//    @GET("api/service/service")
//    suspend fun getService(
//        @Query("service_id") service_id: String
//    ): Response<ServiceResponse>
//
//    suspend fun sendInvoice(
//        @Query("id") id: String,
//        @Query("message") message: String
//    ): Response<List<MessageResponse>>
//
//    @FormUrlEncoded
//    @POST("/api/invoice/ticket")
//    suspend fun createTicket(
//        @Field("price") price: String,
//        @Field("concept") concept: String,
//        @Field("email") email: String
//    ): Response<Operation>
//
//
//
//    @GET("api/driver/service")
//    suspend fun getOneService(
//        @Query("lat") lat: Double?,
//        @Query("lng") lng: Double?,
//        @Query("id") id: String
//    ): Response<ServiceResponse>


    //endregion Others
}