package com.avaliacao.veiculos.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.itextpdf.io.image.ImageDataFactory
import com.itextpdf.kernel.colors.ColorConstants
import com.itextpdf.kernel.pdf.PdfDocument
import com.itextpdf.kernel.pdf.PdfWriter
import com.itextpdf.layout.Document
import com.itextpdf.layout.element.Image
import com.itextpdf.layout.element.Paragraph
import com.itextpdf.layout.element.Table
import com.itextpdf.layout.properties.TextAlignment
import com.itextpdf.layout.properties.UnitValue
import java.io.ByteArrayOutputStream
import java.io.File
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

data class AvaliacaoPDF(
    val placa: String,
    val modelo: String,
    val ano: Int,
    val dataAvaliacao: Date,
    val itens: List<ItemAvaliacaoPDF>,
    val imagens: List<String>,
    val valorTotal: Double,
    val observacoes: String?
)

data class ItemAvaliacaoPDF(
    val nome: String,
    val categoria: String?,
    val estado: String?,
    val quantidade: Int,
    val valor: Double,
    val fornecedor: String?,
    val observacoes: String?
)

class PDFGenerator(private val context: Context) {

    private val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale("pt", "BR"))
    private val currencyFormat = NumberFormat.getCurrencyInstance(Locale("pt", "BR"))

    fun gerarPDF(avaliacao: AvaliacaoPDF, outputFile: File): Boolean {
        return try {
            val writer = PdfWriter(outputFile)
            val pdfDoc = PdfDocument(writer)
            val document = Document(pdfDoc)

            // Título
            val titulo = Paragraph("AVALIAÇÃO DE VEÍCULO SEMINOVO")
                .setFontSize(18f)
                .setBold()
                .setTextAlignment(TextAlignment.CENTER)
            document.add(titulo)

            document.add(Paragraph("\n"))

            // Informações do veículo
            adicionarInformacoesVeiculo(document, avaliacao)

            document.add(Paragraph("\n"))

            // Tabela de itens
            adicionarTabelaItens(document, avaliacao.itens)

            document.add(Paragraph("\n"))

            // Valor total
            val valorTotal = Paragraph("VALOR TOTAL: ${currencyFormat.format(avaliacao.valorTotal)}")
                .setFontSize(14f)
                .setBold()
                .setFontColor(ColorConstants.BLUE)
                .setTextAlignment(TextAlignment.RIGHT)
            document.add(valorTotal)

            // Observações
            if (!avaliacao.observacoes.isNullOrEmpty()) {
                document.add(Paragraph("\n"))
                document.add(Paragraph("Observações:").setBold())
                document.add(Paragraph(avaliacao.observacoes))
            }

            // Imagens
            if (avaliacao.imagens.isNotEmpty()) {
                document.add(Paragraph("\n"))
                document.add(Paragraph("Imagens:").setBold())
                adicionarImagens(document, avaliacao.imagens)
            }

            // Rodapé
            document.add(Paragraph("\n\n"))
            val rodape = Paragraph("Relatório gerado em ${dateFormat.format(Date())}")
                .setFontSize(10f)
                .setTextAlignment(TextAlignment.CENTER)
                .setFontColor(ColorConstants.GRAY)
            document.add(rodape)

            document.close()
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    private fun adicionarInformacoesVeiculo(document: Document, avaliacao: AvaliacaoPDF) {
        val table = Table(UnitValue.createPercentArray(floatArrayOf(1f, 2f)))
            .useAllAvailableWidth()

        table.addCell(Paragraph("Placa:").setBold())
        table.addCell(Paragraph(avaliacao.placa))

        table.addCell(Paragraph("Modelo:").setBold())
        table.addCell(Paragraph(avaliacao.modelo))

        table.addCell(Paragraph("Ano:").setBold())
        table.addCell(Paragraph(avaliacao.ano.toString()))

        table.addCell(Paragraph("Data da Avaliação:").setBold())
        table.addCell(Paragraph(dateFormat.format(avaliacao.dataAvaliacao)))

        document.add(table)
    }

    private fun adicionarTabelaItens(document: Document, itens: List<ItemAvaliacaoPDF>) {
        document.add(Paragraph("Itens Avaliados:").setBold())

        val table = Table(UnitValue.createPercentArray(floatArrayOf(3f, 2f, 1.5f, 1f, 1.5f)))
            .useAllAvailableWidth()

        // Cabeçalho
        table.addHeaderCell(Paragraph("Item").setBold())
        table.addHeaderCell(Paragraph("Categoria").setBold())
        table.addHeaderCell(Paragraph("Estado").setBold())
        table.addHeaderCell(Paragraph("Qtd").setBold())
        table.addHeaderCell(Paragraph("Valor").setBold())

        // Dados
        for (item in itens) {
            table.addCell(Paragraph(item.nome))
            table.addCell(Paragraph(item.categoria ?: "-"))
            table.addCell(Paragraph(item.estado ?: "-"))
            table.addCell(Paragraph(item.quantidade.toString()))
            table.addCell(Paragraph(currencyFormat.format(item.valor)))
        }

        document.add(table)
    }

    private fun adicionarImagens(document: Document, caminhos: List<String>) {
        for (caminho in caminhos) {
            try {
                val file = File(caminho)
                if (file.exists()) {
                    val bitmap = BitmapFactory.decodeFile(caminho)
                    val stream = ByteArrayOutputStream()
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 80, stream)
                    val byteArray = stream.toByteArray()

                    val imageData = ImageDataFactory.create(byteArray)
                    val image = Image(imageData)
                    
                    // Redimensionar se necessário
                    image.setAutoScale(true)
                    image.setMaxWidth(500f)
                    
                    document.add(image)
                    document.add(Paragraph("\n"))
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

