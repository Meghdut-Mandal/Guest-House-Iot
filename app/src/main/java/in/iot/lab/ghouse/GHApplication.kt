package `in`.iot.lab.ghouse

import android.app.Application
import com.chibatching.kotpref.Kotpref
import io.paperdb.Paper

class GHApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Paper.init(this)
        Kotpref.init(this)
    }

}