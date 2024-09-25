package gvoltr.kmptest.data.db.di

import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import gvoltr.kmptest.data.db.AppDatabase
import gvoltr.kmptest.getDatabaseBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import org.koin.dsl.module

val dbModule = module {

    single<AppDatabase> {
        getDatabaseBuilder()
            .fallbackToDestructiveMigrationOnDowngrade(dropAllTables = true)
            .setDriver(BundledSQLiteDriver())
            .setQueryCoroutineContext(Dispatchers.IO)
            .build()
    }

    single { get<AppDatabase>().nftDao() }

}