package com.samuel.oremoschanganapt.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.samuel.oremoschanganapt.functionsKotlin.ShareIconButton


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Apendice(navController: NavController){
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    val tabs = listOf("Changana", "Português")

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text="Apêndice", color = MaterialTheme.colorScheme.onPrimary) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                ),
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() } ){
                        Icon(imageVector = Icons.Outlined.ArrowBack, contentDescription = "Voltar")
                    }
                }
            )
        },
    ) { innerValues ->
         val bgColor = MaterialTheme.colorScheme.background
         val textColor = MaterialTheme.colorScheme.onPrimary
        Column(
            modifier = Modifier.padding(innerValues)
                .background(bgColor)
        ){
            TabRow(
                selectedTabIndex = selectedTabIndex
            ) {
                tabs.forEachIndexed { index, tab ->
                    Tab(
                        text = { Text(tab, style = MaterialTheme.typography.bodyMedium, color = textColor) },
                        selected = selectedTabIndex == index,
                        onClick = { selectedTabIndex = index },
                    )
                }
            }
            when (selectedTabIndex) {
                0 -> changanaTabContent(Modifier.fillMaxSize(), bgColor)
                1 -> ptTabContent(Modifier.fillMaxSize(), bgColor)
            }
        }
        ShortcutsButton(navController)
    }
}


class Item(val title: String, val subTitle: String)

@Composable
fun ptTabContent(
    modifier: Modifier = Modifier,
    divColor: Color
){
    Column(
        modifier = Modifier.background(divColor)
            .fillMaxSize()
//        .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(10.dp),
        Arrangement.spacedBy(20.dp)
    ) {

        ListWidget("Pentateuco", listOf(listOf(
            Item("Gen.", "Génesis"),
            Item("Ex.", "Êxodo"),
            Item("Lv.", "Levítico"),
            Item("Nm.", "Números"),
            Item("Dt.", "Deuteronómio"),
        )))

        ListWidget("Livros históricos", listOf(listOf(
            Item("Jos.", "Josué"),
            Item("Jz.", "Juízes"),
            Item("Rut.", "Rute"),
            Item("1 Sam.", "1 Samuel"),
            Item("2 Sam.", "2 Samuel"),
            Item("1 Rs.", "1 Reis"),
            Item("2 Rs.", "2 Reis"),
            Item("1 Cr.", "1 Crónicas"),
            Item("2 Cr.", "2 Crónicas"),
            Item("Esd.", "Esdras"),
            Item("Ne.", "Neemias"),
            Item("Tob.", "Tobias"),
            Item("Jdt.", "Judite"),
            Item("Est.", "Ester"),
            Item("1 Mac.", "1 Macabeus"),
            Item("2 Mac.", "2 Macabeus"),
        )))

        ListWidget("Livros sapienciais", listOf(listOf(
            Item("Job", "Job"),
            Item("SI.", "Salmos"),
            Item("Prov.", "Provérbios"),
            Item("Ecle.", "Eclesiastes"),
            Item("Cant.", "Cântico dos Cânticos"),
            Item("Sab.", "Sabedoria"),
            Item("Eclo.", "Eclesiástico"),
        )))

        ListWidget("Profetas", listOf(listOf(
            Item("Is.", "Isais"),
            Item("Jer.", "Jeremias"),
            Item("Lam.", "Lamentações"),
            Item("Bar.", "Baruc"),
            Item("Ez.", "Ezequiel"),
            Item("Dan.", "Daniel"),
            Item("Os.", "Oséias"),
            Item("JL.", "Joel"),
            Item("Am.", "Amós"),
            Item("Adb.", "Adbias"),
            Item("Jn.", "Jonas"),
            Item("Miq.", "Miqueias"),
            Item("Na.", "Naum"),
            Item("Hab.", "Habacuc"),
            Item("Sof.", "Sofonias"),
            Item("Ag.", "Ageu"),
            Item("Zac", "Zacarias"),
            Item("Mal.", "Malaquias"),
            Item("Mt.", "S. Mateus"),
            Item("Me.", "S. Marcos"),
            Item("Le.", "S. Lucas"),
            Item("Jo.", "S. Joao"),
            Item("Act.", "Actos dos Apostolos"),
            Item("Rom.", "Romanos"),
            Item("1 Cor.", "1 Corintos"),
            Item("2 Cor.", "2 Corintos"),
            Item("Gal.", "Galatas"),
            Item("Ef.", "Efesios"),
            Item("Fil.", "Filipenses"),
            Item("Col.", "Colossenses"),
            Item("1 Tes.", "Tessalonicenses"),
            Item("2 Tes.", "Tessalonicenses"),
            Item("1 Tim.", "Timoteo"),
            Item("2 Tim.", "Timoteo"),
            Item("Tit.", "Tito"),
            Item("Fim.", "Filemon"),
            Item("Heb.", "Hebreus"),
            Item("Tgo.", "S. Tiago"),
            Item("1 Ped", "S. Pedro"),
            Item("2 Ped.", "S. Pedro"),
            Item("1 Jo.", "S. Joao"),
            Item("2 Jo.", "S. Joao"),
            Item("3 Jo.", "S. Joao"),
            Item("Jud.", "S. Judas"),
            Item("Ap.", "Apocalipse")
        )))
    }
}

@Composable
fun changanaTabContent(
    modifier: Modifier = Modifier,
    divColor: Color
){
    Column(
        modifier = Modifier.background(divColor)
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(10.dp),
        Arrangement.spacedBy(20.dp)
    ) {
        ListWidget("Pentateuco", listOf(listOf(
            Item("Gen.", "Genesa"),
            Item("Eks", "Eksoda"),
            Item("Levh", "Levhitika"),
            Item("Tinhl", "Tinhlayo"),
            Item("Deut.", "Deuteronome")
        )))

        ListWidget("Livros históricos", listOf(listOf(
            Item("Yox.", "Yoxuwa"),
            Item("Vayav.", "Vayavanyisi"),
            Item("Rhu.", "Rhuti"),
            Item("I Sam.", "I Samiyele"),
            Item("II Sam.", "II Samiyele"),
            Item("I Tih.", "I Tihosi"),
            Item("II Tih.", "II Tihosi"),
            Item("I Tikr.", "I Tikronika"),
            Item("II Tikr.", "II Tikronika"),
            Item("Esr.", "Esra"),
            Item("Neh.", "Nehemiya"),
            Item("Tob.", "Tobiase"),
            Item("Jdt.", "Judite"),
            Item("Est.", "Estere"),
            Item("1. Mac.", "Macabewu"),
            Item("2. Mac.", "Macabewu"),
        )))

        ListWidget("Livros sapienciais", listOf(listOf(
            Item("Yob.", "Yobo"),
            Item("Ps.", "Tipsalema"),
            Item("Swiv.", "Swivuriso"),
            Item("Mudj.", "Mudjondzisi"),
            Item("Ris.", "Risimu ra Tinsimu"),
            Item("Wuth.", "Wuthlary"),
            Item("Ecl.", "Eclesiastike"),
        )))

        ListWidget("Profetas", listOf(listOf(
            Item("Es.", "Esaya"),
            Item("Yer.", "Yeremiya"),
            Item("Swir.", "Swirilo swa Yeremiya"),
            Item("Bar.", "Baruke"),
            Item("Ez.", "Ezekiyele"),
            Item("Dan.", "Daniyele"),
            Item("Hos.", "Hosiya"),
            Item("Yow", "Yowele"),
            Item("Am.", "Amosi"),
            Item("Ob.", "Obadiya"),
            Item("Yon.", "Yonasi"),
            Item("Mik.", "Mikiya"),
            Item("Nah.", "Nahume"),
            Item("Hab.", "Habakuku"),
            Item("Zef.", "Zefaniya"),
            Item("Hag.", "Hagayi"),
            Item("Zak.", "Zakariya"),
            Item("Mal.", "Malakiya"),
            Item("Mt.", "Matewu"),
            Item("Mk.", "Marka"),
            Item("Lk.", "Luka"),
            Item("Yoh.", "Yohane"),
            Item("Mint.", "Mintirho ya Vaapostola"),
            Item("Rom.", "Va le Rhoma"),
            Item("1 Cor.", "Va le Korinto"),
            Item("2 Cor.", "Va le Korinto"),
            Item("Gal.", "Va le Galatiya"),
            Item("Ef.", "Va le Efesa"),
            Item("Fil.", "Va le Filipiya"),
            Item("Col.", "Va le Kolosa"),
            Item("I Tes.", "Va le Tesalonika"),
            Item("II Tes.", "Va le Tesalonika"),
            Item("I Tim", "Timotiya"),
            Item("II Tim", "Timotiya"),
            Item("Tit.", "Tito"),
            Item("Fim.", "Filemoni"),
            Item("Hev.", "Vaheveru"),
            Item("Yak.", "Yakobo"),
            Item("I Pet.", "Petro"),
            Item("II Pet.", "Petro"),
            Item("I Yoh.", "Yohane"),
            Item("II Yoh.", "Yohane"),
            Item("III Yoh.", "Yohane"),
            Item("Yud.", "Yuda"),
            Item("Nhlav.", "Nhlavutelo")
        )))
    }
}


@Composable
fun ListWidget(
    title: String,
    dataList: List<List<Item>>
){
    val divBgColor = MaterialTheme.colorScheme.secondary
    val textColor = MaterialTheme.colorScheme.onPrimary

    Column(
        modifier = Modifier.fillMaxSize()
    ){
        Row (
            Modifier.fillMaxSize().height(40.dp)
                .background(divBgColor, RoundedCornerShape(14.dp, 14.dp, 0.dp, 0.dp))
                .clickable {  }.padding(10.dp),
            Arrangement.SpaceBetween
        ){
            Text("$title", color = textColor)
            Icon(Icons.Default.KeyboardArrowDown, contentDescription = "Open or Close")
        }

        Column(
            Modifier.background(divBgColor, RoundedCornerShape(0.dp, 0.dp, 14.dp, 14.dp)).fillMaxWidth()
        ){

            dataList.forEach { list ->
                list.forEach { item ->
                    Row(
                        Modifier.fillMaxWidth()
                            .padding(5.dp),
                        horizontalArrangement = Arrangement.SpaceAround
                    ){
                        Text(item.title, fontWeight = FontWeight.Bold, textAlign = TextAlign.Justify, color = textColor)
                        Text(item.subTitle, color = textColor)
                    }
                }
            }
        }
    }
}