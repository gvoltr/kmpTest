package gvoltr.kmptest.interop

import gvoltr.kmptest.interop.model.SecurityKey

interface WalletManager {

    fun generateWalletSecKey(): SecurityKey
}