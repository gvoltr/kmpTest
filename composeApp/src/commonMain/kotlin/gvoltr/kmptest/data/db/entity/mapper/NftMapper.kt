package gvoltr.kmptest.data.db.entity.mapper

import gvoltr.kmptest.data.model.profile.WalletNft

fun WalletNft.toDB(ownerAddress: String) = gvoltr.kmptest.data.db.entity.DBNft(
    ownerAddress = ownerAddress,
    collectionId = collectionId,
    name = name,
    collectionLink = collectionLink.orEmpty(),
    imageUrl = imageUrl
)

fun List<WalletNft>.toDB(ownerAddress: String) = map { it.toDB(ownerAddress) }

fun gvoltr.kmptest.data.db.entity.DBNft.toDomain() = WalletNft(
    collectionId = collectionId,
    name = name,
    collectionLink = collectionLink,
    imageUrl = imageUrl
)

fun List<gvoltr.kmptest.data.db.entity.DBNft>.toDomain() = map { it.toDomain() }