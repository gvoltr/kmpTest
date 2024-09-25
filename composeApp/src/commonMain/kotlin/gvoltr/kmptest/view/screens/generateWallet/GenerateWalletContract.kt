package gvoltr.kmptest.view.screens.generateWallet

data class GenerateWalletState(
    val seed: String = "",
)

sealed class GenerateWalletSideEffect

sealed class GenerateWalletUserAction {
    data object GenerateWallet : GenerateWalletUserAction()
}