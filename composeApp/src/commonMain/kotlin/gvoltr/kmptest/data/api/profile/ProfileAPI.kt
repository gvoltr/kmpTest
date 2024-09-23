package gvoltr.kmptest.data.api.profile

import de.jensklingenberg.ktorfit.Response
import de.jensklingenberg.ktorfit.http.GET
import de.jensklingenberg.ktorfit.http.Header
import de.jensklingenberg.ktorfit.http.Path
import de.jensklingenberg.ktorfit.http.Query
import gvoltr.kmptest.data.api.model.WalletNftProfileResponse

interface ProfileAPI {

    @GET("profile/user/{wallet}/nfts")
    suspend fun getProfileNfts(
        @Header("x-api-key") apiKey: String,
        @Path("wallet") wallet: String,
        // ETH/MATIC
        @Query("symbols") symbols: String,
        @Query("cursor") cursor: String? = null,
        @Query("excludeDomains") excludeDomains: Boolean = true,
        @Query("limit") limit: Int = 25
    ): Response<WalletNftProfileResponse>

}