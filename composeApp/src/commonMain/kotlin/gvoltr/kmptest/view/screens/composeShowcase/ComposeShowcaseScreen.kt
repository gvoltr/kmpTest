package gvoltr.kmptest.view.screens.composeShowcase

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.bottomSheet.LocalBottomSheetNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import gvoltr.kmptest.Greeting
import gvoltr.kmptest.view.compose.DefaultClickableIcon
import gvoltr.kmptest.view.compose.DefaultImage
import gvoltr.kmptest.view.compose.DefaultTintIcon
import gvoltr.kmptest.view.compose.GuerrillaText
import gvoltr.kmptest.view.compose.SimpleChart
import gvoltr.kmptest.view.compose.theme.AppColor
import kmptest2.composeapp.generated.resources.Res
import kmptest2.composeapp.generated.resources.compose_multiplatform
import kmptest2.composeapp.generated.resources.ic_back
import kmptest2.composeapp.generated.resources.ic_error
import kmptest2.composeapp.generated.resources.ic_page
import org.jetbrains.compose.resources.painterResource

class ComposeShowcaseScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val bottomSheetNavigator = LocalBottomSheetNavigator.current
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
            Box(modifier = Modifier.fillMaxWidth()) {
                DefaultClickableIcon(
                    painter = painterResource(Res.drawable.ic_back),
                    tint = Color.Black,
                    onClick = { navigator.pop() }
                )
                GuerrillaText(
                    modifier = Modifier.align(Alignment.Center),
                    text = "Compose: $greeting"
                )
            }
            Row {
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
            }
            Button(onClick = { bottomSheetNavigator.show(MPCProgressCardModal()) }) {
                GuerrillaText("show MPC Card")
            }
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
                }
            }
        }
    }
}