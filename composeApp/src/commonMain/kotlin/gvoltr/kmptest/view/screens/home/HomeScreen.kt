package gvoltr.kmptest.view.screens.home

import DefaultSpacer
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import gvoltr.kmptest.view.compose.EditTextFilled
import gvoltr.kmptest.view.compose.GuerrillaText
import gvoltr.kmptest.view.screens.composeShowcase.ComposeShowcaseScreen
import gvoltr.kmptest.view.screens.generateWallet.GenerateWalletScreen
import gvoltr.kmptest.view.viewArch.collectAsState
import org.koin.compose.viewmodel.koinViewModel


class HomeScreen : Screen {
    @Composable
    override fun Content() {
        val viewModel = koinViewModel<HomeViewModel>()

        ScreenContent(viewModel)
    }
}

@Composable
private fun ScreenContent(
    viewModel: HomeViewModel
) {
    val state = viewModel.collectAsState().value
    val navigator = LocalNavigator.currentOrThrow

    Column(
        Modifier
            .fillMaxWidth()
            .verticalScroll(
                rememberScrollState()
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row {
            Button(onClick = { navigator.push(ComposeShowcaseScreen()) }) {
                GuerrillaText("View Examples")
            }
            DefaultSpacer(16.dp)
            Button(onClick = { navigator.push(GenerateWalletScreen()) }) {
                GuerrillaText("Generate Wallet")
            }
        }

        EditTextFilled(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            text = state.walletAddress,
            hint = "hint",
            placeholder = "placeholder",
            imeAction = ImeAction.Done,
            keyboardType = KeyboardType.Text,
            onTextChanged = { viewModel.sendAction(HomeUserAction.UserEnteredAddress(it)) }
        )
    }
}