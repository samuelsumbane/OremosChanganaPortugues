package com.samuel.oremoschanganapt.view.praysPackage

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.FlowColumnScopeInstance.align
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.samuel.oremoschanganapt.R
import com.samuel.oremoschanganapt.components.BottomAppBarPrincipal
import com.samuel.oremoschanganapt.components.LoadingScreen
import com.samuel.oremoschanganapt.components.PrayRow
import com.samuel.oremoschanganapt.components.searchContainer
import com.samuel.oremoschanganapt.components.SidebarNav
import com.samuel.oremoschanganapt.components.buttons.ShortcutsButton
import com.samuel.oremoschanganapt.components.buttons.ScrollToFirstItemBtn
import com.samuel.oremoschanganapt.db.data.Song
import com.samuel.oremoschanganapt.db.data.songsData
import com.samuel.oremoschanganapt.getIdSet
import com.samuelsumbane.oremoschanganapt.db.data.Pray
//import com.samuel.oremoschanganapt.db.CommonViewModel
//import com.samuelsumbane.oremoschanganapt.db.PrayViewModel
import com.samuelsumbane.oremoschanganapt.db.data.praysData
import kotlinx.coroutines.launch
import kotlin.collections.plus


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OracoesPage(navController: NavController) {

    var searchValue by remember { mutableStateOf("") }
    val allPrays by remember { mutableStateOf(praysData) }
    val configuration = LocalConfiguration.current
    val isPortrait = configuration.orientation == android.content.res.Configuration.ORIENTATION_PORTRAIT
    var lovedPraysIds by remember { mutableStateOf(setOf<Int>()) }

    var context = LocalContext.current


    LaunchedEffect(lovedPraysIds) {
        lovedPraysIds = getIdSet(context, "prays_id_set")
        Log.d("prays", "$lovedPraysIds")
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {Text(text = stringResource(R.string.prays), color = MaterialTheme.colorScheme.tertiary)},
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent
                ),
                navigationIcon = {
                    IconButton(onClick={ navController.popBackStack() } ){
                        Icon(imageVector = Icons.Outlined.ArrowBack, contentDescription = null)
                    }
                },
                actions = {
                    searchValue = searchContainer(searchString = searchValue)
                }
            )
        },
        bottomBar = {
            if (isPortrait) BottomAppBarPrincipal(navController, "oracoespage")
        }
    ) { paddingVales ->
        val coroutineScope = rememberCoroutineScope()
        val listState = rememberLazyListState()
        val showUpButton by remember {
            derivedStateOf {
                listState.firstVisibleItemIndex > 0
            }
        }

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

                    Box(modifier = Modifier.weight(1f)) {
                        LazyColumn(
                            state = listState,
                            modifier = Modifier
                                .padding(8.dp)
                                .fillMaxSize(),
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            items (filteredPrays) { pray ->
                                // Each pray row --------->>
                                PrayRow(navController, pray)
                            }
                        }

                        if (showUpButton) {
                            ScrollToFirstItemBtn(modifier = Modifier.align(alignment = Alignment.BottomEnd)){
                                coroutineScope.launch { listState.scrollToItem(0) }
                            }
                        }
                    }
                }

                ShortcutsButton(navController)
            }
        }
    }
}
