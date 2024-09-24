package gvoltr.kmptest.wallet

import gvoltr.kmptest.interop.WalletManager
import gvoltr.kmptest.interop.model.SecurityKey
import wallet.core.jni.HDWallet

class WalletManagerImpl: WalletManager {
    override fun generateWalletSecKey() : SecurityKey = withTrustLib {
        SecurityKey.SeedPhrase(HDWallet(128, "").mnemonic())
    }

    private fun <T> withTrustLib(callback: () -> T): T {
        System.loadLibrary("TrustWalletCore")
        return callback()
    }
}
