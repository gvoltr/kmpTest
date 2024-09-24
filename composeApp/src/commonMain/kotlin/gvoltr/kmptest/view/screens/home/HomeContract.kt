package gvoltr.kmptest.view.screens.home

data class HomeState(
    val walletAddress: String = "",
)

sealed class HomeSideEffect

sealed class HomeUserAction {
    data class UserEnteredAddress(val address: String) : HomeUserAction()
}