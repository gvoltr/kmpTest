package gvoltr.kmptest.view.compose

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.text.font.FontWeight.Companion.W700
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import gvoltr.kmptest.view.compose.theme.AppColor
import gvoltr.kmptest.view.compose.theme.AppColor.Danger
import gvoltr.kmptest.view.compose.theme.AppColor.OnBg
import gvoltr.kmptest.view.compose.theme.AppColor.Primary
import gvoltr.kmptest.view.compose.theme.AppColor.Secondary
import gvoltr.kmptest.view.compose.theme.AppColor.SuccessEmphasis
import kmptest2.composeapp.generated.resources.Res
import kmptest2.composeapp.generated.resources.ic_error
import kmptest2.composeapp.generated.resources.ic_page
import org.jetbrains.compose.resources.painterResource

sealed class MPCCardProgressState {
    data class Loading(
        val progress: Float,
        val infinite: Boolean = false
    ) : MPCCardProgressState()
    data object Success : MPCCardProgressState()
    data object Error : MPCCardProgressState()
}

@Composable
fun MPCProgressStateCard(
    state: MPCCardProgressState,
    title: String,
    subtitle: String,
    plateText: String
) {
    val borderColor by animateColorAsState(targetValue = state.borderColor(), label = "BorderColor")
    Box(
        modifier = Modifier
            .widthIn(200.dp, 340.dp)
            .height(200.dp)
            .border(width = 1.dp, color = borderColor, shape = RoundedCornerShape(12.dp))
            .padding(3.dp)
            .clip(RoundedCornerShape(10.dp))
    ) {
        Background(mpcCardProgressState = state)

        StatusIndicator(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(16.dp),
            state = state
        )

        TitleAndSubtitle(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(16.dp),
            title = title,
            subtitle = subtitle
        )

        NumberPlate(modifier = Modifier.align(Alignment.TopEnd).padding(16.dp), plateText)
    }
}

@Composable
private fun NumberPlate(
    modifier: Modifier,
    plateText: String
) = Box(
    modifier = modifier
        .border(1.dp, AppColor.Surface.copy(alpha = 0.4f), RoundedCornerShape(4.dp))
        .background(White.copy(alpha = 0.4f), RoundedCornerShape(4.dp))
) {
    Text(
        modifier = Modifier.padding(vertical = 4.dp, horizontal = 12.dp),
        text = plateText,
    )
    PlateDot(
        modifier = Modifier
            .align(Alignment.TopStart)
            .padding(4.dp)
    )
    PlateDot(
        modifier = Modifier
            .align(Alignment.TopEnd)
            .padding(4.dp)
    )
    PlateDot(
        modifier = Modifier
            .align(Alignment.BottomStart)
            .padding(4.dp)
    )
    PlateDot(
        modifier = Modifier
            .align(Alignment.BottomEnd)
            .padding(4.dp)
    )
}

@Composable
private fun PlateDot(modifier: Modifier) = Box(
    modifier = modifier
        .size(2.dp)
        .background(White.copy(alpha = 0.6f), CircleShape)
)

@Composable
private fun StatusIndicator(
    modifier: Modifier,
    state: MPCCardProgressState
) {
    when (state) {
        is MPCCardProgressState.Loading ->
            if (state.infinite) {
                CircularProgressIndicator(
                    modifier = modifier.size(48.dp),
                    strokeWidth = 6.dp,
                    backgroundColor = Secondary.copy(alpha = 0.5f),
                    strokeCap = StrokeCap.Round
                )
            } else {
                CircularProgressIndicator(
                    modifier = modifier.size(48.dp),
                    progress = state.progress,
                    strokeWidth = 6.dp,
                    backgroundColor = Secondary.copy(alpha = 0.5f),
                    strokeCap = StrokeCap.Round
                )
            }
        is MPCCardProgressState.Success -> DefaultTintIcon(
            modifier = modifier.size(48.dp),
            painter = painterResource(Res.drawable.ic_page),
            tint = White
        )

        is MPCCardProgressState.Error -> DefaultTintIcon(
            modifier = modifier.size(48.dp),
            painter = painterResource(Res.drawable.ic_error),
            tint = Danger
        )
    }
}

@Composable
private fun TitleAndSubtitle(
    modifier: Modifier,
    title: String,
    subtitle: String,
) = Column(modifier = modifier) {
    Text(
        text = title,
        fontSize = 24.sp,
        lineHeight = 28.sp,
        fontWeight = W700,
        maxLines = 2,
        overflow = TextOverflow.Ellipsis,
    )
    Text(
        text = subtitle,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis
    )
}

@Composable
private fun Background(mpcCardProgressState: MPCCardProgressState) {
    val borderColor by animateColorAsState(
        targetValue = mpcCardProgressState.backgroundColor(),
        label = "BackgroundColor"
    )
    Box(
        Modifier
            .fillMaxSize()
            .background(color = borderColor)
    )
    DottedBackground(mpcCardProgressState)
    GradientLayer()
}

@Composable
fun DottedBackground(mpcCardProgressState: MPCCardProgressState) {
    val colors = mpcCardProgressState.backgroundDotsColors()
    val dotDiameter = 1.dp
    val dotRadius = dotDiameter / 2
    val dotSpacing = 3.dp
    val totalSpacing = dotDiameter + dotSpacing

    val randomColors = remember { mutableStateOf(List(0) { colors.random() }) }

    Canvas(modifier = Modifier.fillMaxSize()) {
        val canvasWidth = size.width
        val canvasHeight = size.height
        val horizontalDots = (canvasWidth / totalSpacing.toPx()).toInt() + 1
        val verticalDots = (canvasHeight / totalSpacing.toPx()).toInt() + 1

        if (randomColors.value.size != horizontalDots * verticalDots) {
            randomColors.value = List(horizontalDots * verticalDots) { colors.random() }
        }

        for (i in 0 until horizontalDots) {
            for (j in 0 until verticalDots) {
                val color = randomColors.value[i * verticalDots + j]
                val x = (i * totalSpacing.toPx()) + dotRadius.toPx()
                val y = (j * totalSpacing.toPx()) + dotRadius.toPx()
                drawCircle(color = color, radius = dotRadius.toPx(), center = Offset(x, y))
            }
        }
    }
}

@Composable
private fun GradientLayer() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        Color.Transparent,
                        White.copy(alpha = 0.1f),
                        Color.Transparent,
                        White.copy(alpha = 0.1f),
                        Color.Transparent
                    ),
                    start = Offset.Zero,
                    end = Offset.Infinite, // This will create a 45 degree angle
                    tileMode = TileMode.Mirror // This will create the wave effect
                )
            )
    )
}

@Composable
private fun MPCCardProgressState.borderColor(): Color {
    return when (this) {
        is MPCCardProgressState.Loading -> Primary
        is MPCCardProgressState.Success -> SuccessEmphasis
        is MPCCardProgressState.Error -> Danger
    }
}

@Composable
private fun MPCCardProgressState.backgroundColor(): Color {
    return when (this) {
        is MPCCardProgressState.Loading -> OnBg.copy(alpha = 0.1f)
        is MPCCardProgressState.Success -> SuccessEmphasis.copy(alpha = 0.9f)
        is MPCCardProgressState.Error -> OnBg.copy(alpha = 0.1f)
    }
}

@Composable
private fun MPCCardProgressState.backgroundDotsColors(): List<Color> {
    val disabledDot = White.copy(alpha = 0.1f)
    val progressDot = Primary.copy(alpha = 0.8f)
    val successDot = White.copy(alpha = 0.6f)
    val errorDot = Danger.copy(alpha = 0.6f)

    return when (this) {
        is MPCCardProgressState.Loading -> listOf(
            progressDot,
            disabledDot,
            disabledDot,
            disabledDot,
            disabledDot,
            disabledDot
        )

        is MPCCardProgressState.Success -> listOf(
            successDot,
            disabledDot,
            disabledDot,
            disabledDot,
            disabledDot,
            disabledDot
        )

        is MPCCardProgressState.Error -> listOf(
            errorDot,
            disabledDot,
            disabledDot,
            disabledDot,
            disabledDot,
            disabledDot
        )
    }
}
