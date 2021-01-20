package `in`.iot.lab.ghouse.db

import com.chibatching.kotpref.KotprefModel

object LocalDb : KotprefModel() {
    var name by stringPref("None")
    var email by stringPref("None")
    var isLoggedIn by booleanPref(false)
}