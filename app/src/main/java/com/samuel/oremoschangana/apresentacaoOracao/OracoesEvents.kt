package com.samuel.oremoschangana.apresentacaoOracao

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.samuel.oremoschangana.dataOracao.Cancao

sealed interface OracoesEvent {
    
    data class SaveOracao(
        val titulo: String,
        val subTitulo: String,
        val corpo: String,
        val favorito: Boolean
    ): OracoesEvent

    data class UpdateFavorito(
        val oracaoId: Int,
        val novoFavorito: Boolean
    ) : OracoesEvent
}

sealed interface CancaoEvent{
    data class SaveCancao(
        val numero: String,
        val grupo: String,
        val titulo: String,
        val subTitulo: String,
        val corpo: String,
        val favorito: Boolean
    ): CancaoEvent

    data class UpdateFavorito(
        val cancaoId: Int,
        val novoFavorito: Boolean
    ) : CancaoEvent

}

data class CancaoState(
    val cancoes: List<Cancao> = emptyList(),
    val numero: MutableState<String> = mutableStateOf(""),
    val grupo: MutableState<String> = mutableStateOf(""),
    val titulo: MutableState<String> = mutableStateOf(""),
    val subTitulo: MutableState<String> = mutableStateOf(""),
    val corpo: MutableState<String> = mutableStateOf(""),
    val favorito: MutableState<Boolean> = mutableStateOf(false)
)