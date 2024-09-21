package gvoltr.kmptest.data.api.model

data class WalletNftProfileResponse(
//    @SerializedName("ETH")
    val eth: Chain?,
//    @SerializedName("MATIC")
    val matic: Chain?
) {

    fun getChain() = eth ?: matic

    data class Chain(
        val nfts: List<Nft>,
        val cursor: String?
    )

    data class Nft(
        val mint: String,
        val link: String,
        val collection: String,
        val collectionOwners: Int,
        val collectionLink: String?,
        val collectionImageUrl: String?,
        val name: String,
        val description: String,
//        @SerializedName("image_url")
        val imageUrl: String,
        val tags: List<String>?,
        val createdDate: String?,
        val acquiredDate: String?,
        val saleDetails: SaleDetails?,
        val floorPrice: FloorPrice?,
        val rarity: Rarity?,
        val traits: Map<String, String>?,
        val supply: Int
    )

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
            val cost: Double,
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