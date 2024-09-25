import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp

@Composable
fun DefaultSpacer(size: Dp) = Spacer(modifier = Modifier.size(size))

@Composable
fun ColumnScope.DefaultFillSpacer(weight: Float = 1f) = Spacer(modifier = Modifier.weight(weight))

@Composable
fun RowScope.DefaultFillSpacer(weight: Float = 1f) = Spacer(modifier = Modifier.weight(weight))