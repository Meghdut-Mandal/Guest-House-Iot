package `in`.iot.lab.ghouse.models


data class Booking(
    val id: String,
    val startTime: Long,
    val endTime: Long,
    val roomId: String,
    val customerId: String,
    val authorId: String,
    val paymentsId: String
)


