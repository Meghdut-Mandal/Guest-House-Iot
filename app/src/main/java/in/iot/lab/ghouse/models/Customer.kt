package `in`.iot.lab.ghouse.models

import androidx.annotation.Keep

@Keep
data class Customer(
    var name: String,
    var phoneNumber: String
) {
    constructor() : this( "", "")
}

