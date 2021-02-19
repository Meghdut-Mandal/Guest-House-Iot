package `in`.iot.lab.ghouse.models

import androidx.annotation.Keep

@Keep
data class PaymentItem(val roomCode: String, val payment: Payment)