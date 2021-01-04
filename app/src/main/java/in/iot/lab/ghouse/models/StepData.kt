package `in`.iot.lab.ghouse.models

import java.util.*

data class Duration(val start: Date = Calendar.getInstance().time, val end: Date = start)

data class CustomerDetails(var name: String = "", var phoneNumber: String = "")
data class PaymentDetails(var type: String = "", var info: String="")
