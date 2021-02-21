package `in`.iot.lab.ghouse.db

import java.lang.Exception

sealed class Resource<out R> {

    object Loading : Resource<Nothing>()

    class Success<out T>(val value: T) : Resource<T>()

    class Failure(val exception: Exception) : Resource<Nothing>()

}