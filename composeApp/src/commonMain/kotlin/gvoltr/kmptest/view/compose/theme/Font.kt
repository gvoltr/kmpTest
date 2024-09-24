package gvoltr.kmptest.view.compose.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import kmptest2.composeapp.generated.resources.Res
import kmptest2.composeapp.generated.resources.guerrilla_regular
import org.jetbrains.compose.resources.Font

@Composable
fun GuerrillaFont() = FontFamily(
    Font(Res.font.guerrilla_regular, FontWeight.Normal)
)
