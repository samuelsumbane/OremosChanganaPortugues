package com.samuel.oremoschanganapt.view.songsPackage

import android.util.Log
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentTransitionScope.SlideDirection.Companion.Left
import androidx.compose.animation.AnimatedContentTransitionScope.SlideDirection.Companion.Right
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.tween
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.samuel.oremoschanganapt.R
import com.samuel.oremoschanganapt.SetIdPreference
import com.samuel.oremoschanganapt.components.BottomAppBarPrincipal
import com.samuel.oremoschanganapt.components.LoadingScreen
import com.samuel.oremoschanganapt.components.SidebarNav
import com.samuel.oremoschanganapt.components.SongRow
import com.samuel.oremoschanganapt.components.buttons.ScrollToFirstItemBtn
import com.samuel.oremoschanganapt.components.buttons.ShortcutsButton
import com.samuel.oremoschanganapt.components.searchContainer
import com.samuel.oremoschanganapt.components.textFontSize
import com.samuel.oremoschanganapt.components.toastAlert
import com.samuel.oremoschanganapt.db.data.Song
import com.samuel.oremoschanganapt.db.data.songsData
import com.samuel.oremoschanganapt.functionsKotlin.isNumber
import com.samuel.oremoschanganapt.getIdSet
import com.samuel.oremoschanganapt.repository.ColorObject
import com.samuel.oremoschanganapt.saveIdSet
import com.samuel.oremoschanganapt.view.states.AppState.isSearchInputVisible
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SongsPage(navController: NavController, value: String, readbleValue: String,
) {

    var searchValue by remember { mutableStateOf("") }
    var advancedSearchString by remember { mutableStateOf("") }
    val allSongs = songsData
    var activeInput by remember { mutableIntStateOf(0) }
    var searchInputActive by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) }

    val configuration = LocalConfiguration.current
    val isPortrait = configuration.orientation == android.content.res.Configuration.ORIENTATION_PORTRAIT
    val context = LocalContext.current
    var lovedSongsIds by remember { mutableStateOf(setOf<Int>()) }

    LaunchedEffect(Unit) {
        lovedSongsIds = getIdSet(context, SetIdPreference.SONGS_ID.preferenceName)
    }


    val data = when(value){
        "todos" -> allSongs
        "new" -> allSongs.filter{ it.number.startsWith("0") }
        else -> allSongs.filter{ it.group == value }
    }

    LaunchedEffect(activeInput) {
        searchValue = ""
        advancedSearchString = ""
    }


    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    if (!isSearchInputVisible) Text(
                        text = readbleValue
                            .replaceFirstChar { e -> e.uppercase() }
                            .replace(" | ", "\n"),
                        fontSize = if ("|" in readbleValue) (textFontSize().value - 1).sp else textFontSize(),
                        color = MaterialTheme.colorScheme.tertiary,
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent
                ),
                navigationIcon = {
                    IconButton(onClick = { navController.navigate("canticosAgrupados") }) {
                        Icon(imageVector = Icons.Outlined.ArrowBack, contentDescription = null)
                    }
                },
                actions = {
                    Row ( modifier = Modifier.padding(50.dp, 0.dp, 0.dp, 0.dp) ) {

                        AnimatedContent(
                            targetState = activeInput,
                            transitionSpec = {
                                slideIntoContainer(
                                    animationSpec = tween(400, easing = EaseIn),
                                    towards = Left
                                ).togetherWith(
                                    slideOutOfContainer(
                                        animationSpec = tween(450, easing = EaseOut),
                                        towards = Right
                                    )
                                )
                            },
                        ) { activeInput ->
                            when (activeInput) {
                                0 -> searchContainer("Pesquisar cântico") {
                                    searchValue = it
                                }
                                1 -> searchContainer("Pesquisa avançada") {
                                    advancedSearchString = it
                                }
                            }
                        }

                        Row (
                            modifier = Modifier
                                .padding(0.dp, 7.dp, 0.dp, 0.dp)
                                .width(40.dp)
                        ) {
                            IconButton(
                                modifier = Modifier,
                                onClick = {
                                   activeInput = if (activeInput == 0) 1 else 0
                                   searchInputActive = !searchInputActive
                                   if (searchInputActive) {
                                       toastAlert(
                                           context,
                                           text = "Pesquisa avançada activada\n" +
                                                   "Encontra o cântico pelo seu conteúdo"

                                       )
                                   }
                                }
                            ) {
                                Icon(
                                    imageVector = ImageVector.vectorResource(id = R.drawable.advanced_search),
                                    contentDescription = "Trocar o campo de pesquisa",
                                    tint = if (searchInputActive) ColorObject.mainColor else MaterialTheme.colorScheme.tertiary,
                                    modifier = Modifier.size(33.dp).padding(top=6.dp),
                                )
                            }
                        }
                    }
                }
            )
        },
        bottomBar = {
            if (isPortrait) BottomAppBarPrincipal(navController, "canticosAgrupados")
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
            allSongs.isEmpty() -> LoadingScreen()
            else -> {
                val filteredSongs = remember(data, searchValue, advancedSearchString) {
                    if (searchValue.isNotBlank()) {
                        val numOrNot = isNumber(searchValue)
                        if (numOrNot) {
                            data.filter { it.number == searchValue }
                        } else {
                            data.filter {
                                it.title.contains(searchValue, ignoreCase = true)
                            }
                        }
                    } else if (advancedSearchString.isNotBlank()) {
                        val numOrNot = isNumber(advancedSearchString)
                        if (numOrNot) {
                            data.filter { it.number == advancedSearchString }
                        } else {
                            data.filter {
                                it.title.contains(advancedSearchString, ignoreCase = true) ||
                                        it.body.contains(advancedSearchString, ignoreCase = true)
                            }
                        }
                    } else data
                }

                Row(Modifier.fillMaxSize().padding(paddingVales)) {
                    if (!isPortrait) SidebarNav(navController, "canticosAgrupados")

                    Box(modifier = Modifier.weight(1f)) {
                        LazyColumn(
                            state = listState,
                            modifier = Modifier.fillMaxSize().padding(5.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            items(filteredSongs) {
                                SongRow(
                                    navController,
                                    song = it,
                                    loved = it.id in lovedSongsIds,
                                    onToggleLoved = { id ->
                                        coroutineScope.launch {
                                            val newSet = lovedSongsIds.toMutableSet().apply {
                                                if (contains(id)) remove(id) else add(id)
                                            }
                                            saveIdSet(context, newSet, SetIdPreference.SONGS_ID.preferenceName)
                                            lovedSongsIds = newSet
                                        }
                                    }
                                )
                            }
                        }

                        if (showUpButton) {
                            ScrollToFirstItemBtn(modifier = Modifier.align(alignment = Alignment.BottomEnd)){
                                coroutineScope.launch {
                                    listState.scrollToItem(0)
                                }
                            }
                        }
                    }
                }
                ShortcutsButton(navController)
            }
        }
    }
}
