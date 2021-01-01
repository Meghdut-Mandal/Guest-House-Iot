package `in`.iot.lab.ghouse

import android.app.Application
import io.paperdb.Paper

class GHApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Paper.init(this)
    }

}