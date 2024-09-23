package gvoltr.kmptest.di

import gvoltr.kmptest.data.api.apiModule
import gvoltr.kmptest.data.dataModule
import org.koin.dsl.KoinAppDeclaration

fun koinConfiguration(): KoinAppDeclaration = {
    modules(apiModule, dataModule, vmModule)
}