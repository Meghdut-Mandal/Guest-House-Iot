package `in`.iot.lab.ghouse.db

import java.lang.Exception

sealed class Resource<out R> {

    object Loading : Resource<Nothing>()

    class Success<out T>(val value: T) : Resource<T>()

    class Faliure(val exception: Exception) : Resource<Nothing>()

}