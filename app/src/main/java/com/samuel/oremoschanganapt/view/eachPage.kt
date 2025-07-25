package com.samuel.oremoschanganapt.view

import Reminder
import ReminderRepository
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.PopupProperties
import androidx.navigation.NavController
import com.samuel.oremoschanganapt.R
import com.samuel.oremoschanganapt.SetIdPreference
import com.samuel.oremoschanganapt.components.StarButton
import com.samuel.oremoschanganapt.components.pagerContent
import com.samuel.oremoschanganapt.components.toastAlert
import com.samuel.oremoschanganapt.db.data.songsData
import com.samuel.oremoschanganapt.functionsKotlin.DataCollection
import com.samuel.oremoschanganapt.functionsKotlin.shareText
import com.samuel.oremoschanganapt.getIdSet
import com.samuel.oremoschanganapt.saveIdSet
import com.samuel.oremoschanganapt.view.states.UIState.isFullScreen
import com.samuel.oremoschanganapt.db.data.praysData
import com.samuel.oremoschanganapt.functionsKotlin.getCurrentTimestamp
import kotlinx.coroutines.launch
import kotlin.collections.component1
import kotlin.collections.component2
import kotlin.collections.iterator



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun eachPage(
    navController: NavController,
    dataCollection: DataCollection,
    itemId: Int,
) {
    val reminder = stringResource(R.string.reminder)
    val share = stringResource(R.string.share)
    val fullscreen = stringResource(R.string.fullscreen)
    var expanded by remember { mutableStateOf(false) }

    var songOrPrayId by remember { mutableIntStateOf(0) }
    var pageNumber by remember { mutableStateOf("") }
    var pageTitle by remember { mutableStateOf("") }
    var pageSubTitle by remember { mutableStateOf("") }
    var pageBody by remember { mutableStateOf("") }

    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    var lovedState by remember { mutableStateOf(false) }
    var lovedIds by remember { mutableStateOf(setOf<Int>()) }


    LaunchedEffect(lovedIds) {
        lovedIds = getIdSet(context, SetIdPreference.SONGS_ID.preferenceName)
        lovedState = itemId in lovedIds
    }

    val btnsIcons = mapOf(
        reminder to Icons.Default.Notifications,
        share to Icons.Default.Share,
        fullscreen to ImageVector.vectorResource(R.drawable.fullscreen)
    )
    val data = if (dataCollection == DataCollection.SONGS) songsData else praysData

//    val scrollState = rememberScrollState()
    val pagerState = rememberPagerState(
        initialPage = itemId - 1, pageCount = { data.size }
    )

    /**
     * In pagerState, initialPage receives songId - 1 because, will be page + 1
     * in page inside HorizontalPager
     */

    @Composable
    fun pager(
        modifier: Modifier,
        showShortcutButton: Boolean = true
    ) {
        HorizontalPager(state = pagerState, pageSpacing = 15.dp) { page ->

            if (data == songsData) {
                songsData.first { it.id == page + 1 }.run {
                    pagerContent(
                        navController = navController,
                        modifier = modifier,
                        title = "$number - ${title.uppercase()}",
                        subTitle = subTitle,
                        body = body,
                        showShortcutButton = showShortcutButton
                    )
                    pageNumber = number
                    pageTitle = "$number - ${title.uppercase()}"
                    pageSubTitle = subTitle
                    pageBody = body
                    songOrPrayId = id
                }
            } else {
                praysData.first { it.id == page + 1 }.run {
                    pagerContent(
                        navController = navController,
                        modifier = modifier,
                        title = title.uppercase(),
                        subTitle = subTitle,
                        body = body,
                        showShortcutButton = showShortcutButton
                    )
                    pageTitle = title.uppercase()
                    pageSubTitle = subTitle
                    pageBody = body
                    songOrPrayId = id
                }
            }
        }
    }

    if (isFullScreen) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            IconButton(
                onClick = {
                    isFullScreen = false
                    expanded = false
                },
                modifier = Modifier.align(Alignment.End)
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.fullscreen_exit),
                    contentDescription = "Toggle fullscreen",
                    tint = MaterialTheme.colorScheme.tertiary
                )
            }
            pager(modifier = Modifier, showShortcutButton = false)
        }
    } else {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = if (dataCollection == DataCollection.SONGS) stringResource(R.string.song) else stringResource(R.string.pray) ,
                            color = MaterialTheme.colorScheme.tertiary
                        )
                    }, colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.background
                    ), navigationIcon = {
                        IconButton(onClick = {
                            if (data == songsData) {
                                navController.navigate("canticospage/todos/todos cânticos")
                            } else {
                                navController.navigate("oracoespage")
                            }
                        }) {
                            Icon(
                                imageVector = Icons.Outlined.ArrowBack,
                                contentDescription = stringResource(R.string.go_back)
                            )
                        }
                    }, actions = {
                        val context = LocalContext.current
                        // ---------->>
                        StarButton(lovedState) {
                            coroutineScope.launch {
                                if (lovedState) {
                                    lovedIds.toMutableSet().remove(itemId)
                                } else {
                                    lovedIds.toMutableSet().add(itemId)
                                }
                                saveIdSet(
                                    context,
                                    lovedIds,
                                    preferencesString = if (data == songsData) SetIdPreference.SONGS_ID.preferenceName
                                    else SetIdPreference.PRAYS_ID.preferenceName
                                )
//                            Log.d("songs", "to save $lovedSongsIds")
                                lovedState = !lovedState
                            }
                        }

                        IconButton(onClick = { expanded = !expanded }) {
                            Icon(
                                Icons.Default.MoreVert,
                                contentDescription = stringResource(R.string.options)
                            )
                            DropdownMenu(
                                expanded = expanded,
                                onDismissRequest = { expanded = false },
                                properties = PopupProperties(focusable = true),
                                modifier = Modifier.shadow(
                                    elevation = 3.dp,
                                    spotColor = Color.DarkGray
                                )
                            ) {
                                for((name, icon) in btnsIcons) {
                                    DropdownMenuItem(
                                        modifier = Modifier.fillMaxWidth(),
                                        text = { Text(name) },
                                        trailingIcon = {
                                            Icon(
                                                imageVector = icon,
                                                contentDescription = "l",
                                                Modifier.size(18.dp)
                                            )
                                        },
                                        onClick = {
                                            when (name) {
                                                reminder -> {
                                                    navController.navigate("configurereminder/$songOrPrayId/${if (data == songsData) "Song" else "Pray"}/0")
                                                }

                                                share -> {
                                                    shareText(
                                                        context,
                                                        text = if (data == songsData)
                                                            "$pageNumber - $pageTitle \n $pageBody"
                                                        else "$pageTitle \n $pageBody"
                                                    )
                                                }

                                                fullscreen -> isFullScreen = true
                                            }
                                        }
                                    )
                                }
                            }
                        }
                    })
            }) { paddingValues ->
            pager(modifier = Modifier.padding(paddingValues))
        }

    }

}