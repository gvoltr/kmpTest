package gvoltr.kmptest.data

import gvoltr.kmptest.data.repository.ProfileRepository
import org.koin.dsl.module

val dataModule = module {
    single { ProfileRepository(get()) }
}