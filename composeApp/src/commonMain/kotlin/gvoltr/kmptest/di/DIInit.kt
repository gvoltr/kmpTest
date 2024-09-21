package gvoltr.kmptest.di

import org.koin.dsl.KoinAppDeclaration

fun koinConfiguration(): KoinAppDeclaration = {
    modules(vmModule())
}