package gvoltr.kmptest.view.compose

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import kmptest2.composeapp.generated.resources.Res
import kmptest2.composeapp.generated.resources.ic_back
import org.jetbrains.compose.resources.painterResource

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

@Composable
fun DefaultClickableIcon(
    modifier: Modifier = Modifier,
    painter: Painter,
    tint: Color,
    onClick: () -> Unit,
) = DefaultTintIcon(
    modifier = modifier
        .size(36.dp)
        .clip(CircleShape)
        .clickable(onClick = onClick)
        .padding(8.dp),
    painter = painterResource(Res.drawable.ic_back),
    tint = Color.Black
)
