package `in`.iot.lab.ghouse.models

import androidx.annotation.Keep

@Keep
data class Payment(
        val paymentMethod: String,
        val paymentAdvance: Int,
        val paymentRemaining: Int
) {
    constructor() : this("", 0, 0)
}


