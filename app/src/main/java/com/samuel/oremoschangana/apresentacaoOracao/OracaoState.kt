package com.samuel.oremoschangana.apresentacaoOracao


import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.samuel.oremoschangana.dataOracao.Cancao
import com.samuel.oremoschangana.dataOracao.Oracao


data class OracaoState(
    val oracoes: List<Oracao> = emptyList(),
    val titulo: MutableState<String> = mutableStateOf(""),
    val subTitulo: MutableState<String> = mutableStateOf(""),
    val corpo: MutableState<String> = mutableStateOf(""),
    val favorito: MutableState<Boolean> = mutableStateOf(false)
)

