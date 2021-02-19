package `in`.iot.lab.ghouse.models

import androidx.annotation.Keep

@Keep
sealed class Request {
    object ReloadViewModel: Request()
}