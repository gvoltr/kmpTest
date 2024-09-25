package gvoltr.kmptest

import androidx.room.RoomDatabase
import gvoltr.kmptest.data.db.AppDatabase

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform

expect fun getDatabaseBuilder(): RoomDatabase.Builder<AppDatabase>