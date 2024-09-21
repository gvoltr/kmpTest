package gvoltr.kmptest.compose

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.dp


@Composable
fun SimpleChart(
    modifier: Modifier = Modifier,
    values: List<Float> = listOf(
        7.48f, 0f, 7.59f, 7.98f, 5.4f, 4.3f, 8f
    ),
    graphColor: Color = Color.Blue
) {
    var height = 0f

    Canvas(
        modifier = modifier
            .onGloballyPositioned { coordinates ->
                height = coordinates.size.height.toFloat()
            }
    ) {
        var largestY = values.max()

        val yPoints = if (values.size == 1 || largestY == 0f) {
            listOf(height / 2, height / 2)
        } else {
            values.map { value ->
                (1 - value / largestY) * height
            }
        }
        val spacePerHour = (size.width) / (yPoints.size - 1)

        var lastX = 0f
        val strokePath = Path().apply {

            for (i in yPoints.indices) {
                val currentX = i * spacePerHour

                if (i == 0) {
                    moveTo(currentX, yPoints[i])
                } else {

                    val previousX = (i - 1) * spacePerHour

                    val conX1 = (previousX + currentX) / 2f
                    val conX2 = (previousX + currentX) / 2f

                    val conY1 = yPoints[i - 1]
                    val conY2 = yPoints[i]

                    cubicTo(
                        x1 = conX1,
                        y1 = conY1,
                        x2 = conX2,
                        y2 = conY2,
                        x3 = currentX,
                        y3 = yPoints[i]
                    )
                }

                lastX = currentX
            }
        }

        drawPath(
            path = strokePath,
            color = graphColor,
            style = Stroke(
                width = 2.dp.toPx(),
                cap = StrokeCap.Round
            )
        )

        val fillPath = strokePath
            .apply {
                lineTo(lastX, size.height)
                lineTo(0f, size.height)
                close()
            }
        drawPath(
            path = fillPath,
            brush = Brush.verticalGradient(
                colors = listOf(
                    graphColor.copy(alpha = 0.3f),
                    Color.Transparent,
                ),
                endY = size.height
            )
        )
    }
}