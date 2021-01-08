package `in`.iot.lab.ghouse.db

import `in`.iot.lab.ghouse.models.Booking
import `in`.iot.lab.ghouse.models.Customer
import `in`.iot.lab.ghouse.models.Payment
import `in`.iot.lab.ghouse.models.Room
import `in`.iot.lab.ghouse.ui.main.NewBooking
import retrofit2.Call
import retrofit2.http.*

interface GHInterface {

//    @GET("booking")
//    fun getBookings(@Query("checkIn") checkIn: String?,@Query("checkOut") checkOut: String?): List<Booking>

    @GET("booking/{id}")
    fun getBookings(@Path("id") id: String): Call<Booking>

    @GET("room/{id}")
    fun getRoom(@Path("id") id: String): Call<Room>

    @GET("customer/{id}")
    fun getCustomer(@Path("id") id: String): Call<Customer>

    @POST("customer/create")
    fun createCustomer(@Body newCustomer: Customer) : Call<Customer>

    @POST("payment/create")
    fun createCustomer(@Body newPayment: Payment) : Call<Payment>

    @POST("room/create")
    fun createRoom(@Body newRoom: Room): Call<Room>

    @POST("booking/create")
    fun createbooking(@Body newBooking: NewBooking) : Call<Booking>







}