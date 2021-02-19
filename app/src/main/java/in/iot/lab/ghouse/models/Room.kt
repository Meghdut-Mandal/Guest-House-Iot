package `in`.iot.lab.ghouse.models

import androidx.annotation.Keep


@Keep
data class Room(
    val id: String,
    val roomCode: String,
    val desc: String?
) {
    constructor() : this("", "", "")
}
