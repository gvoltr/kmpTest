package gvoltr.kmptest.screens.home

import gvoltr.kmptest.viewArch.BaseViewModel

class HomeViewModel() : BaseViewModel<HomeState, HomeSideEffect, HomeUserAction>(
    HomeState()
) {
    override fun processUserAction(action: HomeUserAction) {
        when (action) {
            is HomeUserAction.UserEnteredAddress -> {
                updateState { state -> state.copy(walletAddress = action.address) }
            }
        }
    }
}