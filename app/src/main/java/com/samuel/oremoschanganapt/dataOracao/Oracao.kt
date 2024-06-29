package com.samuel.oremoschanganapt.dataOracao

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Oracao(
    val titulo: String,
    val subTitulo: String,
    val corpo: String,
    val favorito: Boolean,

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)

@Entity
data class Cancao(
    val numero: String,
    val grupo: String,
    val titulo: String,
    val subTitulo: String,
    val corpo: String,
    val favorito: Boolean,

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)
