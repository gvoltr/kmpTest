package gvoltr.kmptest.data.model.mapping

import gvoltr.kmptest.data.api.model.WalletNftProfileResponse
import gvoltr.kmptest.data.model.profile.WalletNft

fun WalletNftProfileResponse.toWalletNfts(): List<WalletNft> {
    val ethNfts = eth?.nfts?.map { it.toDataWalletNft() } ?: emptyList()
    val maticNfts =
        matic?.nfts?.map { it.toDataWalletNft() } ?: emptyList()
    return ethNfts + maticNfts
}

private fun WalletNftProfileResponse.Nft.toDataWalletNft(): WalletNft {
    return WalletNft(
        collectionLink = collectionLink.orEmpty(),
        collectionId = collection,
        name = name,
        imageUrl = imageUrl,
    )
}
