import SwiftUI
import ComposeApp

@main
struct iOSApp: App {
    
    init() {
        CustomKoinInitializer().doInitKoin(walletManager: WalletManagerImpl())
    }

    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}
