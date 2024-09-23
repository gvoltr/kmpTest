package gvoltr.kmptest.data.model.mapping

import gvoltr.kmptest.data.api.model.WalletNftProfileResponse
import gvoltr.kmptest.data.model.profile.WalletNft

fun WalletNftProfileResponse.toWalletNfts(): List<WalletNft> {
    val ethNfts = eth?.nfts?.map { it.toDataWalletNft(WalletNft.Blockchain.ETH) } ?: emptyList()
    val maticNfts =
        matic?.nfts?.map { it.toDataWalletNft(WalletNft.Blockchain.MATIC) } ?: emptyList()
    return ethNfts + maticNfts
}

private fun WalletNftProfileResponse.Nft.toDataWalletNft(blockchain: WalletNft.Blockchain): WalletNft {
    return WalletNft(
        blockchain = blockchain,
        mint = mint,
        link = link,
        collection = collection,
        collectionOwners = collectionOwners,
        collectionLink = collectionLink,
        collectionImageUrl = collectionImageUrl,
        name = name,
        description = description,
        imageUrl = imageUrl,
        tags = tags,
        createdDate = createdDate,
        acquiredDate = acquiredDate,
        saleDetails = saleDetails?.toDataWalletNftSaleDetails(),
        floorPrice = floorPrice?.toDataWalletNftFloorPrice(),
        rarity = rarity?.toDataWalletNftRarity(),
        traits = traits,
        supply = supply
    )
}

private fun WalletNftProfileResponse.SaleDetails.toDataWalletNftSaleDetails(): WalletNft.SaleDetails {
    return WalletNft.SaleDetails(
        primary = primary?.toDataWalletNftSaleDetailsPrimary(),
        secondary = secondary?.map { it.toDataWalletNftSaleDetailsSecondary() }
    )
}

@Suppress("MaxLineLength")
private fun WalletNftProfileResponse.SaleDetails.Primary.toDataWalletNftSaleDetailsPrimary(): WalletNft.SaleDetails.Primary {
    return WalletNft.SaleDetails.Primary(
        date = date,
        txHash = txHash,
        marketPlace = marketPlace
    )
}

@Suppress("MaxLineLength")
private fun WalletNftProfileResponse.SaleDetails.Secondary.toDataWalletNftSaleDetailsSecondary(): WalletNft.SaleDetails.Secondary {
    return WalletNft.SaleDetails.Secondary(
        type = type,
        date = date,
        cost = cost,
        payment = payment.toDataWalletNftSaleDetailsSecondaryPayment()
    )
}

@Suppress("MaxLineLength")
private fun WalletNftProfileResponse.SaleDetails.Secondary.Payment.toDataWalletNftSaleDetailsSecondaryPayment(): WalletNft.SaleDetails.Secondary.Payment {
    return WalletNft.SaleDetails.Secondary.Payment(
        symbol = symbol,
        valueUsd = valueUsd,
        valueNative = valueNative
    )
}

private fun WalletNftProfileResponse.FloorPrice.toDataWalletNftFloorPrice(): WalletNft.FloorPrice {
    return WalletNft.FloorPrice(
        currency = currency,
        value = value
    )
}

private fun WalletNftProfileResponse.Rarity.toDataWalletNftRarity(): WalletNft.Rarity {
    return WalletNft.Rarity(
        rank = rank,
        score = score
    )
}
