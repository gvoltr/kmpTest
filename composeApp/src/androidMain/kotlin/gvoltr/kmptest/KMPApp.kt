package gvoltr.kmptest

import android.app.Application

class KMPApp: Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {
        lateinit var instance: KMPApp
            private set
    }
}