@file:Suppress("unused")

package `in`.iot.lab.ghouse.models


data class Booking(
    val id: String,
    val startTime: Long,
    val endTime: Long,
    val customer: Customer?,
    val authorId: String?,
    val room: String?,
    val payment: Payment?
) {
    constructor() : this("", 0L, 0L, null, "", "", null)
}


