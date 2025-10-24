package com.avaliacao.veiculos.api

import com.avaliacao.veiculos.model.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    // Veículos
    @GET("veiculos")
    suspend fun getVeiculos(): Response<VeiculosResponse>

    @GET("veiculos/{id}")
    suspend fun getVeiculo(@Path("id") id: Int): Response<Veiculo>

    @GET("veiculos/placa/{placa}")
    suspend fun getVeiculoPorPlaca(@Path("placa") placa: String): Response<Veiculo>

    @POST("veiculos")
    suspend fun criarVeiculo(@Body veiculo: Veiculo): Response<SuccessResponse>

    @PUT("veiculos/{id}")
    suspend fun atualizarVeiculo(@Path("id") id: Int, @Body veiculo: Veiculo): Response<SuccessResponse>

    @DELETE("veiculos/{id}")
    suspend fun deletarVeiculo(@Path("id") id: Int): Response<SuccessResponse>

    // Itens
    @GET("itens")
    suspend fun getItens(@Query("categoria") categoria: String? = null): Response<ItensResponse>

    @GET("itens/{id}")
    suspend fun getItem(@Path("id") id: Int): Response<Item>

    @POST("itens")
    suspend fun criarItem(@Body item: Item): Response<SuccessResponse>

    @PUT("itens/{id}")
    suspend fun atualizarItem(@Path("id") id: Int, @Body item: Item): Response<SuccessResponse>

    @DELETE("itens/{id}")
    suspend fun deletarItem(@Path("id") id: Int): Response<SuccessResponse>

    @GET("itens/categorias/lista")
    suspend fun getCategorias(): Response<Map<String, List<String>>>

    // Fornecedores
    @GET("fornecedores")
    suspend fun getFornecedores(@Query("tipo_servico") tipoServico: String? = null): Response<FornecedoresResponse>

    @GET("fornecedores/{id}")
    suspend fun getFornecedor(@Path("id") id: Int): Response<Fornecedor>

    @POST("fornecedores")
    suspend fun criarFornecedor(@Body fornecedor: Fornecedor): Response<SuccessResponse>

    @PUT("fornecedores/{id}")
    suspend fun atualizarFornecedor(@Path("id") id: Int, @Body fornecedor: Fornecedor): Response<SuccessResponse>

    @DELETE("fornecedores/{id}")
    suspend fun deletarFornecedor(@Path("id") id: Int): Response<SuccessResponse>

    // Avaliações
    @GET("avaliacoes")
    suspend fun getAvaliacoes(): Response<AvaliacoesResponse>

    @GET("avaliacoes/{id}")
    suspend fun getAvaliacao(@Path("id") id: Int): Response<AvaliacaoResponse>

    @POST("avaliacoes")
    suspend fun criarAvaliacao(@Body avaliacao: Avaliacao): Response<SuccessResponse>

    @PUT("avaliacoes/{id}")
    suspend fun atualizarAvaliacao(@Path("id") id: Int, @Body avaliacao: Avaliacao): Response<SuccessResponse>

    @DELETE("avaliacoes/{id}")
    suspend fun deletarAvaliacao(@Path("id") id: Int): Response<SuccessResponse>

    // Itens da Avaliação
    @POST("avaliacoes/{id}/itens")
    suspend fun adicionarItemAvaliacao(@Path("id") avaliacaoId: Int, @Body item: ItemAvaliacao): Response<SuccessResponse>

    @PUT("avaliacoes/{id}/itens/{item_id}")
    suspend fun atualizarItemAvaliacao(
        @Path("id") avaliacaoId: Int,
        @Path("item_id") itemId: Int,
        @Body item: ItemAvaliacao
    ): Response<SuccessResponse>

    @DELETE("avaliacoes/{id}/itens/{item_id}")
    suspend fun deletarItemAvaliacao(@Path("id") avaliacaoId: Int, @Path("item_id") itemId: Int): Response<SuccessResponse>

    @POST("avaliacoes/{id}/calcular-total")
    suspend fun calcularTotalAvaliacao(@Path("id") avaliacaoId: Int): Response<SuccessResponse>

    // Imagens
    @Multipart
    @POST("imagens/upload")
    suspend fun uploadImagem(
        @Part imagem: MultipartBody.Part,
        @Part("avaliacao_id") avaliacaoId: RequestBody,
        @Part("tipo") tipo: RequestBody
    ): Response<SuccessResponse>

    @GET("imagens/avaliacao/{avaliacao_id}")
    suspend fun getImagensAvaliacao(@Path("avaliacao_id") avaliacaoId: Int): Response<ImagensResponse>

    @DELETE("imagens/{id}")
    suspend fun deletarImagem(@Path("id") id: Int): Response<SuccessResponse>
}

