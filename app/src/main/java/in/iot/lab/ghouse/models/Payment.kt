package `in`.iot.lab.ghouse.models

data class Payment(
    val paymentMethod: String,
    val info: String
) {
    constructor() : this("", "")
}
