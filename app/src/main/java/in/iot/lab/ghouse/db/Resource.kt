package `in`.iot.lab.ghouse.db

import java.lang.Exception

sealed class Resource {

    object Loading : Resource()

    class Success<T>(val value: T) : Resource()

    class Faliure(val exception: Exception) : Resource()

}