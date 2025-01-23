import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color

@Composable
fun DrawCircle(modifier: Modifier = Modifier, color: Color) {
    Box(
        modifier = modifier.drawBehind {
            // Set the radius based on the smaller of the box dimensions
            val radius = size.minDimension / 2
            drawCircle(
                color = color,
                radius = radius,
                center = center
            )
        }
    )
}
