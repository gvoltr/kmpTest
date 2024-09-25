package gvoltr.kmptest.view.screens.generateWallet

import DefaultSpacer
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import gvoltr.kmptest.view.compose.DefaultClickableIcon
import gvoltr.kmptest.view.compose.GuerrillaText
import gvoltr.kmptest.view.viewArch.collectAsState
import kmptest2.composeapp.generated.resources.Res
import kmptest2.composeapp.generated.resources.ic_back
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel

class GenerateWalletScreen : Screen {
    @Composable
    override fun Content() {
        val viewModel = koinViewModel<GenerateWalletViewModel>()
        val state = viewModel.collectAsState().value
        val navigator = LocalNavigator.currentOrThrow

        Column(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .verticalScroll(
                    rememberScrollState()
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(modifier = Modifier.fillMaxWidth()) {
                DefaultClickableIcon(
                    painter = painterResource(Res.drawable.ic_back),
                    tint = Color.Black,
                    onClick = { navigator.pop() }
                )
                GuerrillaText(
                    modifier = Modifier.align(Alignment.Center),
                    text = "Generate Wallet"
                )
            }

            DefaultSpacer(16.dp)

            Button(onClick = { viewModel.sendAction(GenerateWalletUserAction.GenerateWallet) }) {
                GuerrillaText("Generate new wallet")
            }

            DefaultSpacer(16.dp)
            GuerrillaText(
                text = "New wallet seed:"
            )
            SelectionContainer {
                GuerrillaText(
                    textAlign = TextAlign.Center,
                    text = state.seed,
                    color = Color.Black
                )
            }
        }
    }
}