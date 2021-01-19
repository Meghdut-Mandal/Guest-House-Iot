package `in`.iot.lab.ghouse.models

data class Customer(
    var name: String,
    var phoneNumber: String
) {
    constructor() : this( "", "")
}

