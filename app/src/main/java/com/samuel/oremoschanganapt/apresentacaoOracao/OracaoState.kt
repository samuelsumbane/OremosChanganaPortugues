package com.samuel.oremoschanganapt.apresentacaoOracao


import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.samuel.oremoschanganapt.dataOracao.Oracao


data class OracaoState(
    val oracoes: List<Oracao> = emptyList(),
    val titulo: MutableState<String> = mutableStateOf(""),
    val subTitulo: MutableState<String> = mutableStateOf(""),
    val corpo: MutableState<String> = mutableStateOf(""),
    val favorito: MutableState<Boolean> = mutableStateOf(false)
)

