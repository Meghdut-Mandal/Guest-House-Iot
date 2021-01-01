package `in`.iot.lab.ghouse.models

data class Payment(
    val id: String,
    val paymentMethod: String,
    val customerId: String,
    val time: Long
)
