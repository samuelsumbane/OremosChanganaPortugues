package com.samuel.oremoschangana.apresentacaoOracao

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.samuel.oremoschangana.dataOracao.Oracao
import com.samuel.oremoschangana.dataOracao.OracaoDao
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class OracoesViewModel( private val dao: OracaoDao): ViewModel() {

    private val isSortedById = MutableStateFlow(true)

    @OptIn(ExperimentalCoroutinesApi::class)
    private var oracoes =
        isSortedById.flatMapLatest{ sort ->
            if (sort){
                dao.getOracaOrderById()
            }else{
                dao.getOracaOrderById()
            }
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    val _state = MutableStateFlow(OracaoState())

    val state =
        combine(_state, isSortedById, oracoes){state, isSortedById, oracoes ->
            state.copy(
                oracoes = oracoes
            )
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), OracaoState())

    init {
        viewModelScope.launch {
            val listaOracoes = dao.getOracaOrderById().first()
            if (listaOracoes.isEmpty()) {
                inserirDadosAutomaticamente()
            }
        }
    }

    private fun inserirDadosAutomaticamente() {
        val oracoesParaInserir = oracoesData

        viewModelScope.launch {
            oracoesParaInserir.forEach { dao.upsertOracao(it) }
        }
    }

    fun onEvent(event: OracoesEvent){
        when(event){
            is OracoesEvent.SaveOracao -> {
                val oracao = Oracao(
                    titulo = state.value.titulo.value,
                    subTitulo = state.value.subTitulo.value,
                    corpo = state.value.corpo.value,
                    favorito = state.value.favorito.value
                )
                viewModelScope.launch { dao.upsertOracao(oracao) }
            }

            is OracoesEvent.UpdateFavorito -> {
                viewModelScope.launch {
                    // Obtém a Cancao pelo ID
                    val cancao = dao.getOracaoById(event.oracaoId)
                    // Verifica se a Cancao não é nula e atualiza o valor de favorito
                    cancao?.let {
                        val novaCancao = it.copy(favorito = event.novoFavorito)
                        dao.upsertOracao(novaCancao)
                    }
                }
            }
        }

    }
}