package gvoltr.kmptest.data.db.entity

import androidx.room.Entity

@Entity(
        primaryKeys = [
            "ownerAddress",
            "name"
        ]
)
data class DBNft (
    val ownerAddress: String,
    val collectionId: String,
    val name: String,
    val collectionLink: String,
    val imageUrl: String,
)