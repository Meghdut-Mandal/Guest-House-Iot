package `in`.iot.lab.ghouse.models

data class Payment(
        val paymentMethod: String,
        val paymentAdvance: Int,
        val paymentRemaining: Int
) {
    constructor() : this("", 0, 0)
}


//package `in`.iot.lab.ghouse.models
//
//data class Payment(
//    val paymentMethod: String,
//    val info: String
//) {
//    constructor() : this("", "")
//}
