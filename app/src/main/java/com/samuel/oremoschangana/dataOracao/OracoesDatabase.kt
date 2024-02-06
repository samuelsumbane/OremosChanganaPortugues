package com.samuel.oremoschangana.dataOracao

import androidx.room.*


@Database(
    entities = [Oracao::class],
    version = 1
)

abstract class OracoesDatabase: RoomDatabase() {
    abstract val dao: OracaoDao
}


@Database(
    entities = [Cancao::class],
    version = 1
)
abstract class CancoesDatabase: RoomDatabase(){
    abstract val cdao: CancaoDao
}