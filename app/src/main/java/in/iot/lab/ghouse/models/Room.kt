package `in`.iot.lab.ghouse.models

data class Room(
    val id: String,
    val roomCode: String,
    val desc: String?
) {
    constructor() : this("", "", "")
}
