package gvoltr.kmptest.data.api.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WalletNftProfileResponse(
    @SerialName("ETH")
    val eth: Chain?,
    @SerialName("MATIC")
    val matic: Chain?
) {

    fun getChain() = eth ?: matic

    @Serializable
    data class Chain(
        val nfts: List<Nft>,
        val cursor: String?
    )

    @Serializable
    data class Nft(
        val mint: String,
        val link: String,
        val collection: String,
        val collectionOwners: Int,
        val collectionLink: String?,
        val collectionImageUrl: String?,
        val name: String,
        val description: String,
        @SerialName("image_url")
        val imageUrl: String,
        val tags: List<String>?,
        val createdDate: String?,
        val acquiredDate: String?,
        val saleDetails: SaleDetails?,
        val floorPrice: FloorPrice?,
        val traits: Map<String, String>?
    )

    @Serializable
    data class SaleDetails(
        val primary: Primary?,
        val secondary: List<Secondary>?
    ) {
        @Serializable
        data class Primary(
            val date: String?,
            val txHash: String?,
            val marketPlace: String?,
        )
        @Serializable
        data class Secondary(
            val type: String?,
            val date: String,
            val cost: Double?,
            val payment: Payment
        ) {
            @Serializable
            data class Payment(
                val symbol: String?,
                val valueUsd: Double,
                val valueNative: Double
            )
        }
    }

    @Serializable
    data class FloorPrice(
        val currency: String,
        val value: Double
    )
}