import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons

import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Icon

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
//import androidx.compose.material3.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

import com.samuel.oremoschangana.R


@Composable
fun IconTextButton(
    icon: String,
    text: String,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .clickable { onClick() }
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when (icon){
            "Home" -> {
                Icon(
                    imageVector = Icons.Filled.Home,
                    contentDescription = "",
                    modifier = Modifier.size(25.dp)
                )
            }
            "Oracao" -> {
                Image(
                   imageVector = ImageVector.vectorResource(id = R.drawable.ic_pray), contentDescription = null, modifier = Modifier.size(26.dp)
                )
            }
            "Cantico" -> {
                Image(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_music), contentDescription = null, modifier = Modifier.size(25.dp)
                )
            }
            "Favoritos" -> {
                Icon(
                    imageVector = Icons.Outlined.Star,
                    contentDescription = "",
                    modifier = Modifier.size(25.dp)
                )
            }
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = text,
            modifier = Modifier.padding(4.dp),
            color = White
        )
    }
}

