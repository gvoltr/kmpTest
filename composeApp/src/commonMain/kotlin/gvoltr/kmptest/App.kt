package gvoltr.kmptest

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import gvoltr.kmptest.di.koinConfiguration
import gvoltr.kmptest.screens.home.HomeScreen
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinApplication


@Composable
@Preview
fun App() {
    KoinApplication(koinConfiguration()) {
        MaterialTheme {
            HomeScreen()
        }
    }
}
