package gvoltr.kmptest

import androidx.room.RoomDatabase
import gvoltr.kmptest.data.db.AppDatabase
import gvoltr.kmptest.db.createDatabaseBuilder
import platform.UIKit.UIDevice

class IOSPlatform: Platform {
    override val name: String = UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion
}

actual fun getPlatform(): Platform = IOSPlatform()

actual fun getDatabaseBuilder(): RoomDatabase.Builder<AppDatabase> {
    return createDatabaseBuilder()
}