package com.avaliacao.veiculos.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface VeiculoDao {
    @Query("SELECT * FROM veiculos ORDER BY dataCadastro DESC")
    fun getAllVeiculos(): Flow<List<VeiculoEntity>>

    @Query("SELECT * FROM veiculos WHERE id = :id")
    suspend fun getVeiculoById(id: Int): VeiculoEntity?

    @Query("SELECT * FROM veiculos WHERE placa = :placa LIMIT 1")
    suspend fun getVeiculoByPlaca(placa: String): VeiculoEntity?

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertVeiculo(veiculo: VeiculoEntity): Long

    @Update
    suspend fun updateVeiculo(veiculo: VeiculoEntity)

    @Delete
    suspend fun deleteVeiculo(veiculo: VeiculoEntity)
}

@Dao
interface ItemDao {
    @Query("SELECT * FROM itens WHERE ativo = 1 ORDER BY categoria, nome")
    fun getAllItensAtivos(): Flow<List<ItemEntity>>

    @Query("SELECT * FROM itens WHERE ativo = 1 AND categoria = :categoria ORDER BY nome")
    fun getItensByCategoria(categoria: String): Flow<List<ItemEntity>>

    @Query("SELECT * FROM itens WHERE id = :id")
    suspend fun getItemById(id: Int): ItemEntity?

    @Query("SELECT DISTINCT categoria FROM itens WHERE ativo = 1 AND categoria IS NOT NULL ORDER BY categoria")
    suspend fun getCategorias(): List<String>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertItem(item: ItemEntity): Long

    @Update
    suspend fun updateItem(item: ItemEntity)

    @Query("UPDATE itens SET ativo = 0 WHERE id = :id")
    suspend fun desativarItem(id: Int)
}

@Dao
interface FornecedorDao {
    @Query("SELECT * FROM fornecedores WHERE ativo = 1 ORDER BY nome")
    fun getAllFornecedoresAtivos(): Flow<List<FornecedorEntity>>

    @Query("SELECT * FROM fornecedores WHERE ativo = 1 AND tipoServico = :tipoServico ORDER BY nome")
    fun getFornecedoresByTipo(tipoServico: String): Flow<List<FornecedorEntity>>

    @Query("SELECT * FROM fornecedores WHERE id = :id")
    suspend fun getFornecedorById(id: Int): FornecedorEntity?

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertFornecedor(fornecedor: FornecedorEntity): Long

    @Update
    suspend fun updateFornecedor(fornecedor: FornecedorEntity)

    @Query("UPDATE fornecedores SET ativo = 0 WHERE id = :id")
    suspend fun desativarFornecedor(id: Int)
}

@Dao
interface AvaliacaoDao {
    @Query("""
        SELECT a.id, v.placa, v.modelo, v.ano, a.dataAvaliacao, a.valorTotal, a.status
        FROM avaliacoes a
        INNER JOIN veiculos v ON a.veiculoId = v.id
        ORDER BY a.dataAvaliacao DESC
    """)
    fun getAllAvaliacoes(): Flow<List<AvaliacaoListItem>>

    @Query("SELECT * FROM avaliacoes WHERE id = :id")
    suspend fun getAvaliacaoById(id: Int): AvaliacaoEntity?

    @Transaction
    suspend fun getAvaliacaoCompleta(id: Int): AvaliacaoCompleta? {
        val avaliacao = getAvaliacaoById(id) ?: return null
        val veiculo = getVeiculoByIdSync(avaliacao.veiculoId) ?: return null
        val itensAvaliacao = getItensAvaliacaoByAvaliacaoId(id)
        val imagens = getImagensByAvaliacaoId(id)
        
        val itensCompletos = itensAvaliacao.map { itemAval ->
            val item = getItemByIdSync(itemAval.itemId)!!
            val fornecedor = itemAval.fornecedorId?.let { getFornecedorByIdSync(it) }
            ItemAvaliacaoCompleto(itemAval, item, fornecedor)
        }
        
        return AvaliacaoCompleta(avaliacao, veiculo, itensCompletos, imagens)
    }

    @Query("SELECT * FROM veiculos WHERE id = :id")
    suspend fun getVeiculoByIdSync(id: Int): VeiculoEntity?

    @Query("SELECT * FROM itens WHERE id = :id")
    suspend fun getItemByIdSync(id: Int): ItemEntity?

    @Query("SELECT * FROM fornecedores WHERE id = :id")
    suspend fun getFornecedorByIdSync(id: Int): FornecedorEntity?

    @Query("SELECT * FROM itens_avaliacao WHERE avaliacaoId = :avaliacaoId")
    suspend fun getItensAvaliacaoByAvaliacaoId(avaliacaoId: Int): List<ItemAvaliacaoEntity>

    @Query("SELECT * FROM imagens WHERE avaliacaoId = :avaliacaoId ORDER BY dataCaptura")
    suspend fun getImagensByAvaliacaoId(avaliacaoId: Int): List<ImagemEntity>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertAvaliacao(avaliacao: AvaliacaoEntity): Long

    @Update
    suspend fun updateAvaliacao(avaliacao: AvaliacaoEntity)

    @Delete
    suspend fun deleteAvaliacao(avaliacao: AvaliacaoEntity)

    @Query("SELECT SUM(valorCalculado) FROM itens_avaliacao WHERE avaliacaoId = :avaliacaoId")
    suspend fun calcularValorTotal(avaliacaoId: Int): Double?
}

@Dao
interface ItemAvaliacaoDao {
    @Query("SELECT * FROM itens_avaliacao WHERE avaliacaoId = :avaliacaoId")
    suspend fun getItensByAvaliacaoId(avaliacaoId: Int): List<ItemAvaliacaoEntity>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertItemAvaliacao(item: ItemAvaliacaoEntity): Long

    @Update
    suspend fun updateItemAvaliacao(item: ItemAvaliacaoEntity)

    @Delete
    suspend fun deleteItemAvaliacao(item: ItemAvaliacaoEntity)

    @Query("DELETE FROM itens_avaliacao WHERE id = :id")
    suspend fun deleteItemAvaliacaoById(id: Int)
}

@Dao
interface ImagemDao {
    @Query("SELECT * FROM imagens WHERE avaliacaoId = :avaliacaoId ORDER BY dataCaptura")
    fun getImagensByAvaliacaoId(avaliacaoId: Int): Flow<List<ImagemEntity>>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertImagem(imagem: ImagemEntity): Long

    @Delete
    suspend fun deleteImagem(imagem: ImagemEntity)

    @Query("DELETE FROM imagens WHERE id = :id")
    suspend fun deleteImagemById(id: Int)
}

