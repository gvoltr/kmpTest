package gvoltr.kmptest.data.db.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import gvoltr.kmptest.data.db.entity.DBNft
import kotlinx.coroutines.flow.Flow

@Dao
interface NftDao {

    @Upsert
    suspend fun upsert(nfts: List<DBNft>)

    @Query("SELECT DISTINCT ownerAddress FROM DBNft")
    fun observeSavedWallets(): Flow<List<String>>

    @Query("SELECT * FROM DBNft WHERE ownerAddress = :wallet COLLATE NOCASE")
    fun observeNftsForWallet(wallet: String): Flow<List<DBNft>>

    @Query("SELECT * FROM DBNft WHERE ownerAddress = :wallet COLLATE NOCASE")
    suspend fun getNftsForWallet(wallet: String): List<DBNft>

}