package com.example.thegiftcherk.setup.network


import android.content.Context
import com.example.thegiftcherk.BuildConfig
import com.example.thegiftcherk.R
import com.example.thegiftcherk.features.ui.search.models.Item
import com.example.thegiftcherk.setup.MOCK_DELAY
import com.example.thegiftcherk.setup.network.NetworkExceptionController.checkException
import com.example.thegiftcherk.setup.network.NetworkExceptionController.checkResponse
import com.example.thegiftcherk.setup.utils.extensions.getJsonFromResource
import com.google.gson.Gson
import kotlinx.coroutines.delay

class Repository(private val service: Service, private val context: Context) {
    //region User
//    suspend fun doLogin(
//        email: String, pass: String, FCMToken: String?,
//        fake: Boolean = BuildConfig.MOCK
//    ): ResponseResult<User> {
//        return if (!fake) {
//            try {
//                val response = service.login(email, pass, FCMToken)
//                checkResponse(context, response)
//            } catch (e: Exception) {
//                checkException(context, e)
//            }
//        } else {
//            delay(MOCK_DELAY)
//            context.getMockResponseResult(R.raw.user)
//        }
//    }

//    suspend fun rememberPass(
//        email: String,
//        fake: Boolean = BuildConfig.MOCK
//    ): ResponseResult<Operation> {
//        return if (!fake) {
//            try {
//                val response = service.rememberPass(email)
//                checkResponse(context, response)
//            } catch (e: Exception) {
//                checkException(context, e)
//            }
//        } else {
//            context.getMockResponseResult(R.raw.user)
//        }
//    }

//    suspend fun notifyCustomer(
//        serviceId: String?,
//        fake: Boolean = BuildConfig.MOCK
//    ): ResponseResult<Any> {
//        return if (!fake) {
//            try {
//                val response = service.notifyCustomer(serviceId)
//                checkResponse(context, response)
//            } catch (e: Exception) {
//                checkException(context, e)
//            }
//        } else {
//            context.getMockResponseResult(R.raw.user)
//        }
//    }

//    suspend fun changeState(
//        token: String,
//        fake: Boolean = BuildConfig.MOCK
//    ): ResponseResult<State> {
//        return if (!fake) {
//            try {
//                val response = service.changeState(token)
//                checkResponse(context, response)
//            } catch (e: Exception) {
//                checkException(context, e)
//            }
//
//        } else {
//            delay(MOCK_DELAY)
//            context.getMockResponseResult(R.raw.state)
//        }
//    }

    //quitar parametro
//    suspend fun getAmountOfDay(
//        fake: Boolean = BuildConfig.MOCK
//    ): ResponseResult<String> {
//        return if (!fake) {
//            try {
//                val response = service.getAmountOfDay()
//                checkResponse(context, response)
//            } catch (e: Exception) {
//                checkException(context, e)
//            }
//        } else {
//            delay(MOCK_DELAY)
//            context.getMockResponseResult(R.raw.state)
//        }
//    }
//
//    suspend fun getAmountOfMonth(
//        currentMonth: String,
//        fake: Boolean = BuildConfig.MOCK
//    ): ResponseResult<String> {
//        return if (!fake) {
//            try {
//                val response = service.getAmountOfMonth(currentMonth)
//                checkResponse(context, response)
//            } catch (e: Exception) {
//
//            }
//        } else {
//            delay(MOCK_DELAY)
//            context.getMockResponseResult(R.raw.state)
//        }
//    }

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

//    suspend fun getDailySum(
//        fake: Boolean = BuildConfig.MOCK
//    ): ResponseResult<Amount> {
//        return if (!fake) {
//            try {
//                val response = service.getDailySum()
//                checkResponse(context, response)
//            } catch (e: Exception) {
//                checkException(context, e)
//            }
//        } else {
//            delay(MOCK_DELAY)
//            val json = context.getJsonFromResource(R.raw.bookings)
//            val response: Amount = Gson().fromJson(json, Amount::class.java)
//            ResponseResult.Success(response)
//        }
//    }
//
//    suspend fun getDailyInvoices(
//        fake: Boolean = BuildConfig.MOCK
//    ): ResponseResult<List<InvoiceResponse>> {
//        return if (!fake) {
//            try {
//                val response = service.getDailyInvoices()
//                checkResponse(context, response)
//            } catch (e: Exception) {
//                checkException(context, e)
//            }
//        } else {
//            delay(MOCK_DELAY)
//            val json = context.getJsonFromResource(R.raw.bookings)
//            val response = Gson().fromJson(json, Array<InvoiceResponse>::class.java).toList()
//            ResponseResult.Success(response)
//        }
//    }
//
//    suspend fun getMonthlyInvoices(
//        month: Int?,
//        fake: Boolean = BuildConfig.MOCK
//    ): ResponseResult<List<InvoiceResponse>> {
//        return if (!fake) {
//            try {
//                val response = service.getMonthlyInvoices(month)
//                checkResponse(context, response)
//            } catch (e: Exception) {
//                checkException(context, e)
//            }
//        } else {
//            delay(MOCK_DELAY)
//            val json = context.getJsonFromResource(R.raw.bookings)
//            val response = Gson().fromJson(json, Array<InvoiceResponse>::class.java).toList()
//            ResponseResult.Success(response)
//        }
//    }
//
//
//    suspend fun getInvoicePdf(
//        pdf: String?,
//        fake: Boolean = BuildConfig.MOCK
//    ): ResponseResult<Any> {
//        return if (!fake) {
//            try {
//                val response = service.getInvoicePdf(pdf)
//                checkResponse(context, response)
//            } catch (e: Exception) {
//                checkException(context, e)
//            }
//        } else {
//            delay(MOCK_DELAY)
//            val json = context.getJsonFromResource(R.raw.bookings)
//            val response = Gson().fromJson(json, Array<InvoiceResponse>::class.java).toList()
//            ResponseResult.Success(response)
//        }
//    }
//
//
//    suspend fun getBookings(
//        page: Int,
//        fake: Boolean = BuildConfig.MOCK
//    ): ResponseResult<List<BookingResponse>> {
//        return if (!fake) {
//            try {
//                val response = service.getBookings(page)
//                checkResponse(context, response)
//            } catch (e: Exception) {
//                checkException(context, e)
//            }
//        } else {
//            delay(MOCK_DELAY)
//            val json = context.getJsonFromResource(R.raw.bookings)
//            val response: List<BookingResponse> =
//                Gson().fromJson(json, Array<BookingResponse>::class.java).toList()
//            ResponseResult.Success(response)
//        }
//    }
//
//    suspend fun getPaymentMethods(fake: Boolean = BuildConfig.MOCK): ResponseResult<List<PaymentResponse>> {
//        return if (!fake) {
//            try {
//                val response = service.getPayments()
//                checkResponse(context, response)
//            } catch (e: Exception) {
//                checkException(context, e)
//            }
//        } else {
//            delay(MOCK_DELAY)
//            val json = context.getJsonFromResource(R.raw.payments)
//            val response: List<PaymentResponse> =
//                Gson().fromJson(json, Array<PaymentResponse>::class.java).toList()
//            ResponseResult.Success(response)
//        }
//    }
//    //endregion User
//
//    //region Map
//    suspend fun getRoute(
//        origin: String?, destination: String?,
//        fake: Boolean = BuildConfig.MOCK
//    ): ResponseResult<RouteResponse> {
//        return if (!fake) {
//            try {
//                val response = service.getRoute(
//                    origin, destination
//                )
//                checkResponse(context, response)
//            } catch (e: Exception) {
//                checkException(context, e)
//            }
//        } else {
//            val json = context.getJsonFromResource(R.raw.route)
//            val response = Gson().fromJson(json, RouteResponse::class.java)
//            ResponseResult.Success(response)
//        }
//    }
//
//
//    suspend fun getOffers(fake: Boolean = BuildConfig.MOCK): ResponseResult<List<OfferResponse>> {
//        return if (!fake) {
//            try {
//                val response = service.getOffers()
//                checkResponse(context, response)
//            } catch (e: Exception) {
//                checkException(context, e)
//            }
//        } else {
//            delay(MOCK_DELAY)
//            val json = context.getJsonFromResource(R.raw.offers)
//            val response: List<OfferResponse> =
//                Gson().fromJson(json, Array<OfferResponse>::class.java).toList()
//            ResponseResult.Success(response)
//        }
//    }
//
//    suspend fun getServices(
//        lat: Double?,
//        lng: Double?,
//        page: Int,
//        limit: Int,
//        orderBy: String,
//        fake: Boolean = BuildConfig.MOCK
//    ): ResponseResult<List<ServiceResponse>> {
//        return if (!fake) {
//            try {
//                val response = service.getServicesNotAssigned(lat, lng, page, limit, orderBy)
//                checkResponse(context, response)
//            } catch (e: Exception) {
//                checkException(context, e)
//            }
//        } else {
//            delay(MOCK_DELAY)
//            val json = context.getJsonFromResource(R.raw.services)
//            val response: List<ServiceResponse> =
//                Gson().fromJson(json, Array<ServiceResponse>::class.java).toList()
//            ResponseResult.Success(response)
//        }
//    }

//    suspend fun assignService(
//        state: Int?,
//        serviceId: String?,
//        priceTaximeter: Float,
//        fake: Boolean = BuildConfig.MOCK
//    ): ResponseResult<Any> {
//        return if (!fake) {
//            try {
//                val response = service.assignService(state, serviceId, priceTaximeter)
//                checkResponse(context, response)
//            } catch (e: Exception) {
//                checkException(context, e)
//            }
//
//        } else {
//            delay(MOCK_DELAY)
//            context.getMockResponseResult(R.raw.state)
//        }
//    }
//    //endregion Map
//
//    //region Others
//    suspend fun getPlaces(
//        input: String?, googleApiKey: String?,
//        fake: Boolean = BuildConfig.MOCK
//    ): ResponseResult<List<Place>> {
//        return if (!fake) {
//            try {
//                val response = service.getPlaces(input, googleApiKey)
//                checkResponse(context, response)
//            } catch (e: Exception) {
//                checkException(context, e)
//            }
//        } else {
//            delay(MOCK_DELAY)
//            val json = context.getJsonFromResource(R.raw.places)
//            val response: List<Place> = Gson().fromJson(json, Array<Place>::class.java).toList()
//            ResponseResult.Success(response)
//        }
//    }


//    suspend fun getMessages(
//        fake: Boolean = BuildConfig.MOCK
//    ): ResponseResult<List<MessageResponse>> {
//        return if (!fake) {
//            try {
//                val response = service.getMessages()
//                checkResponse(context, response)
//            } catch (e: Exception) {
//                checkException(context, e)
//            }
//        } else {
//            delay(MOCK_DELAY)
//            val json = context.getJsonFromResource(R.raw.messages)
//            val response: List<MessageResponse> =
//                Gson().fromJson(json, Array<MessageResponse>::class.java).toList()
//            ResponseResult.Success(response)
//        }
//    }
//
//    suspend fun sendMessage(
//        title: String,
//        text: String,
//        fake: Boolean = BuildConfig.MOCK
//    ): ResponseResult<Any> {
//        return if (!fake) {
//            try {
//                val response = service.sendMessage(title, text)
//                checkResponse(context, response)
//            } catch (e: Exception) {
//                checkException(context, e)
//            }
//        } else {
//            delay(MOCK_DELAY)
//            val json = context.getJsonFromResource(R.raw.messages)
//            val response: List<MessageResponse> =
//                Gson().fromJson(json, Array<MessageResponse>::class.java).toList()
//            ResponseResult.Success(response)
//        }
//    }
//
//
//    suspend fun getService(
//        service_id: String,
//        fake: Boolean = BuildConfig.MOCK
//    ): ResponseResult<ServiceResponse> {
//
//        return if (!fake) {
//            try {
//
//                val response = service.getService(service_id)
//
//                checkResponse(context, response)
//            } catch (e: Exception) {
//                checkException(context, e)
//            }
//        } else {
//            delay(MOCK_DELAY)
//            context.getMockResponseResult(R.raw.route)
//        }
//    }


//    suspend fun createTicket(
//        price: String,
//        concept: String,
//        email: String,
//        fake: Boolean = BuildConfig.MOCK
//    ): ResponseResult<Operation> {
//        return if (!fake) {
//            try {
//                val response = service.createTicket(price, concept, email)
//                checkResponse(context, response)
//            } catch (e: Exception) {
//                checkException(context, e)
//            }
//        } else {
//            delay(MOCK_DELAY)
//            val json = context.getJsonFromResource(R.raw.messages)
//            val response: Operation =
//                Gson().fromJson(json, Operation::class.java)
//            ResponseResult.Success(response)
//        }
//    }


//    suspend fun getOneService(
//        lat: Double?,
//        lng: Double?,
//        id: String,
//        fake: Boolean = BuildConfig.MOCK
//    ): ResponseResult<ServiceResponse> {
//        return if (!fake) {
//            try {
//
//                val response = service.getOneService(lat, lng, id)
//
//                checkResponse(context, response)
//            } catch (e: Exception) {
//                checkException(context, e)
//            }
//        } else {
//            delay(MOCK_DELAY)
//            context.getMockResponseResult(R.raw.route)
//        }
//    }


    //endregion Others
}


