package gvoltr.kmptest.data.model.profile

data class WalletNft (
    val blockchain: Blockchain,
    val mint: String,
    val link: String,
    val collection: String,
    val collectionOwners: Int,
    val collectionLink: String?,
    val collectionImageUrl: String?,
    val name: String,
    val description: String,
    val imageUrl: String,
    val tags: List<String>?,
    val createdDate: String?,
    val acquiredDate: String?,
    val saleDetails: SaleDetails?,
    val floorPrice: FloorPrice?,
    val rarity: Rarity?,
    val traits: Map<String, String>?,
    val supply: Int
) {
    enum class Blockchain {
        ETH, MATIC
    }

    data class SaleDetails(
        val primary: Primary?,
        val secondary: List<Secondary>?
    ) {
        data class Primary(
            val date: String?,
            val txHash: String?,
            val marketPlace: String?,
        )

        data class Secondary(
            val type: String?,
            val date: String,
            val cost: Double?,
            val payment: Payment
        ) {
            data class Payment(
                val symbol: String?,
                val valueUsd: Double,
                val valueNative: Double
            )
        }
    }

    data class FloorPrice(
        val currency: String,
        val value: Double
    )

    data class Rarity(
        val rank: Int,
        val score: Double
    )
}