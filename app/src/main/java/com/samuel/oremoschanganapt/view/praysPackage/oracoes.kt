package com.samuel.oremoschanganapt.view.praysPackage

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.samuel.oremoschanganapt.components.BottomAppBarPrincipal
import com.samuel.oremoschanganapt.components.LoadingScreen
import com.samuel.oremoschanganapt.components.PrayRow
import com.samuel.oremoschanganapt.components.SearchContainer
import com.samuel.oremoschanganapt.components.SidebarNav
import com.samuel.oremoschanganapt.components.buttons.ShortcutsButton
import com.samuel.oremoschanganapt.db.CommonViewModel
import com.samuelsumbane.oremoschanganapt.db.PrayViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OracoesPage(
    navController: NavController,
    prayViewModel: PrayViewModel,
    commonViewModel: CommonViewModel
) {
    var searchValue by remember { mutableStateOf("") }
    val allPrays by prayViewModel.prays.collectAsState()
    val configuration = LocalConfiguration.current
    val isPortrait = configuration.orientation == android.content.res.Configuration.ORIENTATION_PORTRAIT

    Scaffold(
        topBar = {
            TopAppBar(
                title = {Text(text="Orações", color = MaterialTheme.colorScheme.tertiary)},
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent
                ),
                navigationIcon = {
                    IconButton(onClick={ navController.popBackStack() } ){
                        Icon(imageVector = Icons.Outlined.ArrowBack, contentDescription = null)
                    }
                },
                actions = {
                    searchValue = SearchContainer(searchString = searchValue)
                }
            )
        },
        bottomBar = {
            if (isPortrait) {
                BottomAppBarPrincipal(navController, "oracoespage")
            }
        }
    ) { paddingVales ->

        when {
            allPrays.isEmpty() -> LoadingScreen()
            else -> {
                val filteredPrays = remember(allPrays, searchValue){
                    if (searchValue.isNotEmpty()) {
                        allPrays.filter { it.title.contains(searchValue, ignoreCase = true)}
                    } else {
                        allPrays
                    }
                }

                Row(Modifier.fillMaxSize().padding(paddingVales)) {
                    if (!isPortrait) SidebarNav(navController, "canticosAgrupados")

                    LazyColumn(
                        modifier = Modifier
                            .weight(1f)
                            .padding(8.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ){
                        items (filteredPrays) { pray ->
                            // Each pray row --------->>
                            PrayRow(navController, prayViewModel, pray)
                        }
                    }
                }

                ShortcutsButton(navController)
            }
        }
    }
}
