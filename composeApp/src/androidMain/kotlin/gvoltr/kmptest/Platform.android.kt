package gvoltr.kmptest

import android.os.Build
import androidx.room.RoomDatabase
import gvoltr.kmptest.data.db.AppDatabase

class AndroidPlatform : Platform {
    override val name: String = "Android ${Build.VERSION.SDK_INT}"
}

actual fun getPlatform(): Platform = AndroidPlatform()

actual fun getDatabaseBuilder(): RoomDatabase.Builder<AppDatabase> {
    return gvoltr.kmptest.db.createDBBuilder(ctx = KMPApp.instance.applicationContext)
}