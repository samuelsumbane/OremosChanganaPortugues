
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.samuel.oremoschanganapt.R
import com.samuel.oremoschanganapt.ui.theme.activeIconColor

@Composable
fun IconTextButton(
    icon: String,
    text: String,
    isActive: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .clickable { onClick() }
            .padding(8.dp)
            .drawWithContent {
                drawContent()
                if (isActive) {
                    drawLine(
                        color = activeIconColor,
                        start = Offset(0f, size.height),
                        end = Offset(size.width, size.height),
                        strokeWidth = 3.dp.toPx(),
                    )
                }
            }
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            when (icon) {
                "Home" -> {
                    Icon(
                        imageVector = Icons.Filled.Home,
                        contentDescription = "",
                        modifier = Modifier.size(25.dp),
                        tint = Color.Black
                    )
                }
                "Oracao" -> {
                    Image(
                        imageVector = ImageVector.vectorResource(id = R.drawable.ic_pray),
                        contentDescription = null,
                        modifier = Modifier.size(22.dp),
                    )
                }
                "Cantico" -> {
                    Image(
                        imageVector = ImageVector.vectorResource(id = R.drawable.ic_music),
                        contentDescription = null,
                        modifier = Modifier.size(21.dp),
                    )
                }
                "Favoritos" -> {
                    Icon(
                        imageVector = Icons.Outlined.Star,
                        contentDescription = "",
                        modifier = Modifier.size(25.dp),
                        tint = Color.Black
                    )
                }
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = text,
                modifier = Modifier.padding(4.dp),
                color = Color.Black
            )
        }
    }
}
