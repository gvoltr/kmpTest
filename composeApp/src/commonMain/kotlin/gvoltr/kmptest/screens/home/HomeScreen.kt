package gvoltr.kmptest.screens.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import gvoltr.kmptest.Greeting
import gvoltr.kmptest.compose.EditTextFilled
import gvoltr.kmptest.compose.GuerrillaText
import gvoltr.kmptest.compose.SimpleChart
import gvoltr.kmptest.viewArch.collectAsState
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun HomeScreen() {
    val viewModel = koinViewModel<HomeViewModel>()

    ScreenContent(viewModel)
}

@Composable
private fun ScreenContent(
    viewModel: HomeViewModel
) {
    val state = viewModel.collectAsState().value
    var showContent by remember { mutableStateOf(false) }
    Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        val greeting = remember { Greeting().greet() }
        GuerrillaText("Compose: $greeting")
        Button(onClick = { showContent = !showContent }) {
            GuerrillaText("Click me!!!!!!")
        }
        AnimatedVisibility(showContent) {

            Column(
                Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
//                    Image(painterResource(Res.drawable.compose_multiplatform), null)

                SimpleChart(
                    modifier = Modifier.fillMaxWidth()
                        .height(120.dp)
                )

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
    }
}