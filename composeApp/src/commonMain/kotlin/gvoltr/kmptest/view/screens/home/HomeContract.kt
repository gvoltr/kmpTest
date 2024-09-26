package gvoltr.kmptest.view.screens.home

import gvoltr.kmptest.data.model.profile.WalletNft

private const val COLLECTIONS_HEADER_KEY_TAG = "collectionNameHeader"
private const val COLLECTIONS_ROW_KEY_TAG = "collectionRow"

data class HomeState(
    val walletAddress: String = "",
    val loading: Boolean = false,
    val walletsHistory: List<String> = emptyList(),
    val listItems: List<ListItem> = emptyList()
) {
    sealed class ListItem(val key: String) {
        data class CollectionHeader(
            val name: String,
            val image: String?,
            val nftsCount: Int
        ) : ListItem(key = "$COLLECTIONS_HEADER_KEY_TAG-$name")

        data class NftsRow(val nfts: List<WalletNft>) : ListItem(
            key = "$COLLECTIONS_ROW_KEY_TAG-${nfts.first().hashCode()}"
        )
    }
}

sealed class HomeSideEffect

sealed class HomeUserAction {
    data class UserEnteredAddress(val address: String) : HomeUserAction()
    data object SearchNfts : HomeUserAction()
    data object ClearSearch : HomeUserAction()
}