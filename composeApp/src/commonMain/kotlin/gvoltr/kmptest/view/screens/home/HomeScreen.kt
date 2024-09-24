package gvoltr.kmptest.view.screens.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
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
import gvoltr.kmptest.view.compose.DefaultImage
import gvoltr.kmptest.view.compose.DefaultTintIcon
import gvoltr.kmptest.view.compose.EditTextFilled
import gvoltr.kmptest.view.compose.GuerrillaText
import gvoltr.kmptest.view.compose.SimpleChart
import gvoltr.kmptest.view.compose.theme.AppColor
import gvoltr.kmptest.view.viewArch.collectAsState
import kmptest2.composeapp.generated.resources.Res
import kmptest2.composeapp.generated.resources.compose_multiplatform
import kmptest2.composeapp.generated.resources.ic_error
import kmptest2.composeapp.generated.resources.ic_page
import org.jetbrains.compose.resources.painterResource
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
    Column(
        Modifier
            .fillMaxWidth()
            .verticalScroll(
                rememberScrollState()
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val greeting = remember { Greeting().greet() }
        GuerrillaText("Compose: $greeting")
        DefaultTintIcon(
            modifier = Modifier.size(46.dp),
            painter = painterResource(Res.drawable.ic_error),
            tint = AppColor.Primary
        )
        DefaultTintIcon(
            modifier = Modifier.size(46.dp),
            painter = painterResource(Res.drawable.ic_page),
            tint = AppColor.Primary
        )

        Button(onClick = { showContent = !showContent }) {
            GuerrillaText("Click me!!!!!!")
        }
        AnimatedVisibility(showContent) {

            Column(
                Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                DefaultImage(painter = painterResource(Res.drawable.compose_multiplatform))

                SimpleChart(
                    modifier = Modifier.fillMaxWidth()
                        .height(120.dp)
                )

                CircularProgressIndicator(modifier = Modifier.size(48.dp))

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