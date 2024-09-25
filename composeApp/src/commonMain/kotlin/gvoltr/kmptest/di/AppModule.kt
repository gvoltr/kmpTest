package gvoltr.kmptest.di

import gvoltr.kmptest.view.screens.generateWallet.GenerateWalletViewModel
import gvoltr.kmptest.view.screens.home.HomeViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val vmModule = module {
    viewModel { HomeViewModel(get(), get()) }
    viewModel { GenerateWalletViewModel(get()) }
}