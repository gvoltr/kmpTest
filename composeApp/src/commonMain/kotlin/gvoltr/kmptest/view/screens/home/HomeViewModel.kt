package gvoltr.kmptest.view.screens.home

import gvoltr.kmptest.data.db.dao.NftDao
import gvoltr.kmptest.data.db.entity.mapper.toDB
import gvoltr.kmptest.data.db.entity.mapper.toDomain
import gvoltr.kmptest.data.model.profile.WalletNft
import gvoltr.kmptest.data.repository.ProfileRepository
import gvoltr.kmptest.view.viewArch.BaseViewModel
import kotlinx.coroutines.flow.collectLatest

class HomeViewModel(
    private val profileRepository: ProfileRepository,
    private val nftDao: NftDao
) : BaseViewModel<HomeState, HomeSideEffect, HomeUserAction>(
    HomeState()
) {

    override fun onCreate() {
        super.onCreate()
        observeHistory()
    }

    override fun processUserAction(action: HomeUserAction) {
        when (action) {
            is HomeUserAction.UserEnteredAddress -> {
                updateState { state -> state.copy(walletAddress = action.address) }
            }

            HomeUserAction.ClearSearch -> updateState { state ->
                state.copy(
                    walletAddress = "",
                    listItems = emptyList()
                )
            }

            HomeUserAction.SearchNfts -> loadNfts()
        }
    }

    private fun observeHistory() = intent {
        nftDao.observeSavedWallets().collectLatest {

            println("observeHistory: $it")

            updateState { state -> state.copy(walletsHistory = it) }
        }
    }

    private fun loadNfts() {
        intent {
            reduceState { state.copy(loading = true) }
            val cachedNfts = nftDao.getNftsForWallet(state.walletAddress)

            println("cachedNfts: $cachedNfts")

            if (cachedNfts.isNotEmpty()) {
                showNfts(cachedNfts.toDomain())
            } else {
                val fetchedNfts = profileRepository.getProfileNfts(
                    wallet = state.walletAddress
                )
                if (fetchedNfts.isNotEmpty()) {
                    showNfts(fetchedNfts)
                    nftDao.upsert(fetchedNfts.toDB(state.walletAddress))
                }
            }
            reduceState { state.copy(loading = false) }
        }
    }

    private fun showNfts(nfts: List<WalletNft>) {
        updateState { state ->
            state.copy(
                listItems = nfts.groupBy { it.collectionId }
                    .flatMap { (collection, nfts) ->
                        listOf(
                            HomeState.ListItem.CollectionHeader(
                                name = collection,
                                image = nfts.first().imageUrl,
                                nftsCount = nfts.size
                            )
                        ) + nfts.windowed(
                            2,
                            2,
                            partialWindows = true
                        ).map { HomeState.ListItem.NftsRow(it) }
                    }
            )
        }
    }
}