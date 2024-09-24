package gvoltr.kmptest.interop.model

sealed class SecurityKey(open val value: String) {

    /**
     * For regular wallets.
     * Its guaranteed that value contains correct private key (without hex prefix).
     */
    data class PrivateKey(val privateKey: String) : SecurityKey(privateKey)

    /**
     * For HD wallets (BIP-39). Its guaranteed that value contains correct mnemonics phrase.
     */
    data class SeedPhrase(val seed: String) : SecurityKey(seed)

}