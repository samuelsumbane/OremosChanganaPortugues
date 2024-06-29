package com.samuel.oremoschanganapt.dataOracao

import androidx.room.*
import kotlinx.coroutines.flow.Flow


@Dao
interface OracaoDao {
    @Upsert
    suspend fun upsertOracao(oracao: Oracao)

    @Query("SELECT * FROM oracao WHERE id = :id")
    suspend fun getOracaoById(id: Int): Oracao?

    @Query("SELECT * FROM oracao ORDER BY id")
    fun getOracaOrderById(): Flow<List<Oracao>>
}

@Dao
interface CancaoDao {
    @Upsert
    suspend fun upsertCancao(cancao: Cancao)

    @Query("SELECT * FROM cancao WHERE id = :id")
    suspend fun getCancaoById(id: Int): Cancao?

    @Query("SELECT * FROM cancao ORDER BY id") //acho que terei que chamar por id.
    fun getCancaoOrderByNumero(): Flow<List<Cancao>>
}