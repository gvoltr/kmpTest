package gvoltr.kmptest.compose

import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter

@Composable
fun DefaultTintIcon(
    modifier: Modifier = Modifier,
    painter: Painter,
    tint: Color
) = Icon(
    modifier = modifier,
    painter = painter,
    contentDescription = null,
    tint = tint
)