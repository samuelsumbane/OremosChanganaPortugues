
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
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.samuel.oremoschanganapt.R
import com.samuel.oremoschanganapt.functionsKotlin.colorToString
import com.samuel.oremoschanganapt.repository.colorObject

@Composable
fun IconTextButton(
    icon: String,
    text: String,
    isActive: Boolean,
    iconColor: Color = Color.White,
    textColor: Color = MaterialTheme.colorScheme.onPrimary,
    activeIconColor: Color = lerp(colorObject.mainColor, Color.White, 0.35f),
    onClick: () -> Unit
) {
    val mainColor by remember { mutableStateOf(colorObject.mainColor) }
//    val activeIconColor = lerp(mainColor, Color.White, 0.35f)

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
                        tint = iconColor
                    )
                }
                "Oracao" -> {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.ic_pray),
                        contentDescription = "",
                        modifier = Modifier.size(25.dp),
                        tint = iconColor
                    )
                }
                "Cantico" -> {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.ic_music),
                        contentDescription = null,
                        modifier = Modifier.size(21.dp),
                        tint = iconColor
                    )
                }
                "MorePages" -> {
                    Icon(
                        imageVector = Icons.Outlined.Add,
                        contentDescription = "",
                        modifier = Modifier.size(25.dp),
                        tint = iconColor
                    )
                }
            }
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = text,
                modifier = Modifier.padding(2.dp),
                color = textColor, fontSize = 13.sp
            )
        }
    }
}
