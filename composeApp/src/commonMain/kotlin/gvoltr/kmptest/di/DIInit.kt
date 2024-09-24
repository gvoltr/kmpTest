package gvoltr.kmptest.di

import gvoltr.kmptest.data.api.apiModule
import gvoltr.kmptest.data.dataModule
import gvoltr.kmptest.interop.WalletManager
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.module


class CustomKoinInitializer {
    fun initKoin(
        walletManager: WalletManager
    ) {
        startKoin {
            modules(apiModule, dataModule, vmModule, platformAbstractionModule(walletManager))
        }
    }
}

private fun platformAbstractionModule(walletManager: WalletManager): Module = module {
    single<WalletManager> { walletManager }
}