package com.avaliacao.veiculos.model

import com.google.gson.annotations.SerializedName
import java.util.Date

data class Veiculo(
    @SerializedName("id") val id: Int? = null,
    @SerializedName("placa") val placa: String,
    @SerializedName("modelo") val modelo: String,
    @SerializedName("ano") val ano: Int,
    @SerializedName("data_cadastro") val dataCadastro: Date? = null
)

data class Item(
    @SerializedName("id") val id: Int? = null,
    @SerializedName("nome") val nome: String,
    @SerializedName("categoria") val categoria: String?,
    @SerializedName("valor_padrao") val valorPadrao: Double,
    @SerializedName("descricao") val descricao: String?,
    @SerializedName("ativo") val ativo: Boolean = true
)

data class Fornecedor(
    @SerializedName("id") val id: Int? = null,
    @SerializedName("nome") val nome: String,
    @SerializedName("tipo_servico") val tipoServico: String?,
    @SerializedName("contato") val contato: String?,
    @SerializedName("observacoes") val observacoes: String?,
    @SerializedName("ativo") val ativo: Boolean = true
)

data class Avaliacao(
    @SerializedName("id") val id: Int? = null,
    @SerializedName("veiculo_id") val veiculoId: Int,
    @SerializedName("data_avaliacao") val dataAvaliacao: Date? = null,
    @SerializedName("status") val status: String = "em_andamento",
    @SerializedName("valor_total") val valorTotal: Double = 0.0,
    @SerializedName("observacoes") val observacoes: String?,
    // Campos adicionais para exibição
    @SerializedName("placa") val placa: String? = null,
    @SerializedName("modelo") val modelo: String? = null,
    @SerializedName("ano") val ano: Int? = null,
    @SerializedName("itens") val itens: List<ItemAvaliacao>? = null,
    @SerializedName("imagens") val imagens: List<Imagem>? = null
)

data class ItemAvaliacao(
    @SerializedName("id") val id: Int? = null,
    @SerializedName("avaliacao_id") val avaliacaoId: Int,
    @SerializedName("item_id") val itemId: Int,
    @SerializedName("fornecedor_id") val fornecedorId: Int?,
    @SerializedName("estado") val estado: String?,
    @SerializedName("quantidade") val quantidade: Int = 1,
    @SerializedName("valor_calculado") val valorCalculado: Double = 0.0,
    @SerializedName("observacoes") val observacoes: String?,
    // Campos adicionais para exibição
    @SerializedName("item_nome") val itemNome: String? = null,
    @SerializedName("categoria") val categoria: String? = null,
    @SerializedName("fornecedor_nome") val fornecedorNome: String? = null
)

data class Imagem(
    @SerializedName("id") val id: Int? = null,
    @SerializedName("avaliacao_id") val avaliacaoId: Int,
    @SerializedName("caminho") val caminho: String,
    @SerializedName("tipo") val tipo: String?,
    @SerializedName("data_captura") val dataCaptura: Date? = null,
    @SerializedName("url") val url: String? = null
)

// Respostas da API
data class VeiculosResponse(
    @SerializedName("veiculos") val veiculos: List<Veiculo>
)

data class ItensResponse(
    @SerializedName("itens") val itens: List<Item>
)

data class FornecedoresResponse(
    @SerializedName("fornecedores") val fornecedores: List<Fornecedor>
)

data class AvaliacoesResponse(
    @SerializedName("avaliacoes") val avaliacoes: List<Avaliacao>
)

data class AvaliacaoResponse(
    @SerializedName("avaliacao") val avaliacao: Avaliacao
)

data class ImagensResponse(
    @SerializedName("imagens") val imagens: List<Imagem>
)

data class SuccessResponse(
    @SerializedName("id") val id: Int? = null,
    @SerializedName("message") val message: String,
    @SerializedName("valor_total") val valorTotal: Double? = null
)

data class ErrorResponse(
    @SerializedName("error") val error: String
)

