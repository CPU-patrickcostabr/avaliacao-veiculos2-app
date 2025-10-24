package com.avaliacao.veiculos.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ForeignKey
import androidx.room.Index
import java.util.Date

@Entity(tableName = "veiculos")
data class VeiculoEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val placa: String,
    val modelo: String,
    val ano: Int,
    val dataCadastro: Long = System.currentTimeMillis()
)

@Entity(tableName = "itens")
data class ItemEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nome: String,
    val categoria: String?,
    val valorPadrao: Double,
    val descricao: String?,
    val ativo: Boolean = true
)

@Entity(tableName = "fornecedores")
data class FornecedorEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nome: String,
    val tipoServico: String?,
    val contato: String?,
    val observacoes: String?,
    val ativo: Boolean = true
)

@Entity(
    tableName = "avaliacoes",
    foreignKeys = [
        ForeignKey(
            entity = VeiculoEntity::class,
            parentColumns = ["id"],
            childColumns = ["veiculoId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("veiculoId")]
)
data class AvaliacaoEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val veiculoId: Int,
    val dataAvaliacao: Long = System.currentTimeMillis(),
    val status: String = "em_andamento",
    val valorTotal: Double = 0.0,
    val observacoes: String?
)

@Entity(
    tableName = "itens_avaliacao",
    foreignKeys = [
        ForeignKey(
            entity = AvaliacaoEntity::class,
            parentColumns = ["id"],
            childColumns = ["avaliacaoId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = ItemEntity::class,
            parentColumns = ["id"],
            childColumns = ["itemId"],
            onDelete = ForeignKey.RESTRICT
        ),
        ForeignKey(
            entity = FornecedorEntity::class,
            parentColumns = ["id"],
            childColumns = ["fornecedorId"],
            onDelete = ForeignKey.SET_NULL
        )
    ],
    indices = [Index("avaliacaoId"), Index("itemId"), Index("fornecedorId")]
)
data class ItemAvaliacaoEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val avaliacaoId: Int,
    val itemId: Int,
    val fornecedorId: Int?,
    val estado: String?,
    val quantidade: Int = 1,
    val valorCalculado: Double = 0.0,
    val observacoes: String?
)

@Entity(
    tableName = "imagens",
    foreignKeys = [
        ForeignKey(
            entity = AvaliacaoEntity::class,
            parentColumns = ["id"],
            childColumns = ["avaliacaoId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("avaliacaoId")]
)
data class ImagemEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val avaliacaoId: Int,
    val caminho: String,
    val tipo: String?,
    val dataCaptura: Long = System.currentTimeMillis()
)

// Data classes para consultas complexas
data class AvaliacaoCompleta(
    val avaliacao: AvaliacaoEntity,
    val veiculo: VeiculoEntity,
    val itens: List<ItemAvaliacaoCompleto>,
    val imagens: List<ImagemEntity>
)

data class ItemAvaliacaoCompleto(
    val itemAvaliacao: ItemAvaliacaoEntity,
    val item: ItemEntity,
    val fornecedor: FornecedorEntity?
)

data class AvaliacaoListItem(
    val id: Int,
    val placa: String,
    val modelo: String,
    val ano: Int,
    val dataAvaliacao: Long,
    val valorTotal: Double,
    val status: String
)

