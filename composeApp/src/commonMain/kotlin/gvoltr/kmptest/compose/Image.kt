package gvoltr.kmptest.compose

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale

@Composable
fun DefaultImage(
    modifier: Modifier = Modifier,
    painter: Painter
) = Image(
    modifier = modifier,
    painter = painter,
    contentDescription = null,
    contentScale = ContentScale.FillWidth
)