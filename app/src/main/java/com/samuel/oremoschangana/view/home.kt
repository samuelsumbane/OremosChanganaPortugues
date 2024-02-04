package com.samuel.oremoschangana.view

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.key.Key.Companion.Home
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.semantics.Role.Companion.Button
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.samuel.oremoschangana.R
import com.samuel.oremoschangana.components.BottomAppBarPrincipal
import com.samuel.oremoschangana.ui.theme.HomeColor
import com.samuel.oremoschangana.ui.theme.Purple40
import com.samuel.oremoschangana.ui.theme.PurpleGrey40
import com.samuel.oremoschangana.ui.theme.White

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Home(navController: NavController) {

    Scaffold(
        bottomBar = {
            BottomAppBarPrincipal(navController)
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    color = HomeColor,
                )
        ) {
            Image(
                painter = painterResource(id = R.drawable.cruz),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(.3f)
                    .background(
                        color = Color.Transparent,
                        shape = RoundedCornerShape(0.dp, 0.dp, 0.dp, 40.dp),
                    ),
//                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Oremos",
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    color = Color.White,
                    fontSize = 45.sp
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "A HI KHONGELENI",
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    color = Color.White,
                    fontSize = 30.sp
                )
            }
        }
    }
}
