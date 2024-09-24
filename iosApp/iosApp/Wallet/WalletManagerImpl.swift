//
//  WalletManagerImpl.swift
//  iosApp
//
//  Created by Stanislav Gavrosh on 24.09.2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation
import Web3Core
import web3swift
import ComposeApp

class WalletManagerImpl: NSObject, WalletManager {
    func generateWalletSecKey() -> SecurityKey {
        SecurityKey.SeedPhrase(seed: createMnemonics())
    }
    
    private func createMnemonics() -> String {
        do {
            return try BIP39.generateMnemonics(bitsOfEntropy: 128)!
        } catch {
            print("Error creating mnemonics: \(error)")
            return ""
        }
    }
}
