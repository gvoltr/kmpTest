package gvoltr.kmptest.di

import gvoltr.kmptest.screens.home.HomeViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

fun vmModule() = module {
    viewModel { HomeViewModel() }
}