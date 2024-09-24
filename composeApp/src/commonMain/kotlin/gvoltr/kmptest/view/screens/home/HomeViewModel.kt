package gvoltr.kmptest.view.screens.home

import gvoltr.kmptest.data.repository.ProfileRepository
import gvoltr.kmptest.view.viewArch.BaseViewModel

class HomeViewModel(
    private val profileRepository: ProfileRepository
) : BaseViewModel<HomeState, HomeSideEffect, HomeUserAction>(
    HomeState()
) {

    override fun onCreate() {
        super.onCreate()
        intent {
            val nfts = profileRepository.getProfileNfts(
                wallet = "0x42740d63644db0a8dd8d369d2cb1316d97494de6"
            )
            println(nfts)
        }
    }

    override fun processUserAction(action: HomeUserAction) {
        when (action) {
            is HomeUserAction.UserEnteredAddress -> {
                updateState { state -> state.copy(walletAddress = action.address) }
            }
        }
    }
}