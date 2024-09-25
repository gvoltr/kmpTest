package gvoltr.kmptest.view.screens.composeShowcase

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import gvoltr.kmptest.view.compose.MPCCardProgressState
import gvoltr.kmptest.view.compose.MPCProgressStateCard

class MPCProgressCardModal : Screen {
    @Composable
    override fun Content() = Box(
        Modifier.fillMaxWidth()
            .padding(16.dp)
    ) {
        MPCProgressStateCard(
            state = MPCCardProgressState.Loading(progress = 0.0f, infinite = true),
            title = "Loading",
            subtitle = "(Not real)",
            plateText = "#00007"
        )
    }
}