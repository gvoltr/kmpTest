package gvoltr.kmptest.data.repository

import gvoltr.kmptest.data.api.profile.ProfileAPI
import gvoltr.kmptest.data.model.mapping.toWalletNfts
import gvoltr.kmptest.data.model.profile.WalletNft
import gvoltr.kmptest.secure.Keys
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

class ProfileRepository(
    private val profileAPI: ProfileAPI
) {
    suspend fun getProfileNfts(
        wallet: String,
    ): List<WalletNft> {
        return coroutineScope {
            val ethNfts = async { getProfileNfts(wallet, "ETH") }
            val maticNfts = async { getProfileNfts(wallet, "MATIC") }
            ethNfts.await() + maticNfts.await()
        }
    }

    private suspend fun getProfileNfts(
        wallet: String,
        symbols: String,
    ): List<WalletNft> {
        val nfts = mutableListOf<WalletNft>()
        var cursor: String? = null

        do {
            try {
                profileAPI.getProfileNfts(
                    apiKey = Keys.PROFILE_API_KEY,
                    wallet = wallet,
                    symbols = symbols,
                    cursor = cursor
                ).body()?.let {
                    it.toWalletNfts().also { nfts.addAll(it) }
                    cursor = it.getChain()?.cursor
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        } while (!cursor.isNullOrEmpty())
        return nfts
    }
}