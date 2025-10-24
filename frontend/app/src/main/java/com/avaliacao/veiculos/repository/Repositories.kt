package com.avaliacao.veiculos.repository

import com.avaliacao.veiculos.database.*
import kotlinx.coroutines.flow.Flow

class VeiculoRepository(private val veiculoDao: VeiculoDao) {
    
    val allVeiculos: Flow<List<VeiculoEntity>> = veiculoDao.getAllVeiculos()

    suspend fun getVeiculoById(id: Int): VeiculoEntity? {
        return veiculoDao.getVeiculoById(id)
    }

    suspend fun getVeiculoByPlaca(placa: String): VeiculoEntity? {
        return veiculoDao.getVeiculoByPlaca(placa)
    }

    suspend fun insertVeiculo(veiculo: VeiculoEntity): Long {
        return veiculoDao.insertVeiculo(veiculo)
    }

    suspend fun updateVeiculo(veiculo: VeiculoEntity) {
        veiculoDao.updateVeiculo(veiculo)
    }

    suspend fun deleteVeiculo(veiculo: VeiculoEntity) {
        veiculoDao.deleteVeiculo(veiculo)
    }
}

class ItemRepository(private val itemDao: ItemDao) {
    
    val allItensAtivos: Flow<List<ItemEntity>> = itemDao.getAllItensAtivos()

    fun getItensByCategoria(categoria: String): Flow<List<ItemEntity>> {
        return itemDao.getItensByCategoria(categoria)
    }

    suspend fun getItemById(id: Int): ItemEntity? {
        return itemDao.getItemById(id)
    }

    suspend fun getCategorias(): List<String> {
        return itemDao.getCategorias()
    }

    suspend fun insertItem(item: ItemEntity): Long {
        return itemDao.insertItem(item)
    }

    suspend fun updateItem(item: ItemEntity) {
        itemDao.updateItem(item)
    }

    suspend fun desativarItem(id: Int) {
        itemDao.desativarItem(id)
    }
}

class FornecedorRepository(private val fornecedorDao: FornecedorDao) {
    
    val allFornecedoresAtivos: Flow<List<FornecedorEntity>> = fornecedorDao.getAllFornecedoresAtivos()

    fun getFornecedoresByTipo(tipoServico: String): Flow<List<FornecedorEntity>> {
        return fornecedorDao.getFornecedoresByTipo(tipoServico)
    }

    suspend fun getFornecedorById(id: Int): FornecedorEntity? {
        return fornecedorDao.getFornecedorById(id)
    }

    suspend fun insertFornecedor(fornecedor: FornecedorEntity): Long {
        return fornecedorDao.insertFornecedor(fornecedor)
    }

    suspend fun updateFornecedor(fornecedor: FornecedorEntity) {
        fornecedorDao.updateFornecedor(fornecedor)
    }

    suspend fun desativarFornecedor(id: Int) {
        fornecedorDao.desativarFornecedor(id)
    }
}

class AvaliacaoRepository(
    private val avaliacaoDao: AvaliacaoDao,
    private val itemAvaliacaoDao: ItemAvaliacaoDao,
    private val imagemDao: ImagemDao
) {
    
    val allAvaliacoes: Flow<List<AvaliacaoListItem>> = avaliacaoDao.getAllAvaliacoes()

    suspend fun getAvaliacaoCompleta(id: Int): AvaliacaoCompleta? {
        return avaliacaoDao.getAvaliacaoCompleta(id)
    }

    suspend fun insertAvaliacao(avaliacao: AvaliacaoEntity): Long {
        return avaliacaoDao.insertAvaliacao(avaliacao)
    }

    suspend fun updateAvaliacao(avaliacao: AvaliacaoEntity) {
        avaliacaoDao.updateAvaliacao(avaliacao)
    }

    suspend fun deleteAvaliacao(avaliacao: AvaliacaoEntity) {
        avaliacaoDao.deleteAvaliacao(avaliacao)
    }

    suspend fun addItemToAvaliacao(item: ItemAvaliacaoEntity): Long {
        return itemAvaliacaoDao.insertItemAvaliacao(item)
    }

    suspend fun updateItemAvaliacao(item: ItemAvaliacaoEntity) {
        itemAvaliacaoDao.updateItemAvaliacao(item)
    }

    suspend fun deleteItemAvaliacao(item: ItemAvaliacaoEntity) {
        itemAvaliacaoDao.deleteItemAvaliacao(item)
    }

    suspend fun calcularEAtualizarTotal(avaliacaoId: Int) {
        val total = avaliacaoDao.calcularValorTotal(avaliacaoId) ?: 0.0
        val avaliacao = avaliacaoDao.getAvaliacaoById(avaliacaoId)
        avaliacao?.let {
            avaliacaoDao.updateAvaliacao(it.copy(valorTotal = total))
        }
    }

    suspend fun addImagem(imagem: ImagemEntity): Long {
        return imagemDao.insertImagem(imagem)
    }

    suspend fun deleteImagem(imagem: ImagemEntity) {
        imagemDao.deleteImagem(imagem)
    }

    fun getImagensByAvaliacaoId(avaliacaoId: Int): Flow<List<ImagemEntity>> {
        return imagemDao.getImagensByAvaliacaoId(avaliacaoId)
    }
}

