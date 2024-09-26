package gvoltr.kmptest.view.compose

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun RemoteImage(
    modifier: Modifier,
    url: String
) = com.skydoves.landscapist.coil3.CoilImage(
    modifier = modifier,
    imageModel = { url }
)