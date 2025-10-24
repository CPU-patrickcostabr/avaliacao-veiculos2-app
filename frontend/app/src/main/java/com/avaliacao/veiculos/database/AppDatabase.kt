package com.avaliacao.veiculos.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [
        VeiculoEntity::class,
        ItemEntity::class,
        FornecedorEntity::class,
        AvaliacaoEntity::class,
        ItemAvaliacaoEntity::class,
        ImagemEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    
    abstract fun veiculoDao(): VeiculoDao
    abstract fun itemDao(): ItemDao
    abstract fun fornecedorDao(): FornecedorDao
    abstract fun avaliacaoDao(): AvaliacaoDao
    abstract fun itemAvaliacaoDao(): ItemAvaliacaoDao
    abstract fun imagemDao(): ImagemDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "avaliacao_veiculos_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}

