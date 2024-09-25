package gvoltr.kmptest.view.screens.generateWallet

import gvoltr.kmptest.interop.WalletManager
import gvoltr.kmptest.view.viewArch.BaseViewModel

class GenerateWalletViewModel(
    private val walletManager: WalletManager
) : BaseViewModel<GenerateWalletState, GenerateWalletSideEffect, GenerateWalletUserAction>(
    GenerateWalletState()
) {
    override fun processUserAction(action: GenerateWalletUserAction) {
        when (action) {
            is GenerateWalletUserAction.GenerateWallet -> {
                val freshSeed = walletManager.generateWalletSecKey()
                updateState { it.copy(seed = freshSeed.value) }
            }
        }
    }
}