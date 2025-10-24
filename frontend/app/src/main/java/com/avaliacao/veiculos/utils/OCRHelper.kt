package com.avaliacao.veiculos.utils

import android.graphics.Bitmap
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import java.util.regex.Pattern

class OCRHelper {

    private val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)

    // Padrão de placa brasileira (antiga e Mercosul)
    private val placaAntigaPattern = Pattern.compile("[A-Z]{3}[- ]?\\d{4}")
    private val placaMercosulPattern = Pattern.compile("[A-Z]{3}\\d[A-Z]\\d{2}")

    fun processarImagemPlaca(bitmap: Bitmap, callback: (String?) -> Unit) {
        val image = InputImage.fromBitmap(bitmap, 0)
        
        recognizer.process(image)
            .addOnSuccessListener { visionText ->
                val placaDetectada = extrairPlaca(visionText.text)
                callback(placaDetectada)
            }
            .addOnFailureListener { e ->
                e.printStackTrace()
                callback(null)
            }
    }

    private fun extrairPlaca(texto: String): String? {
        // Remover espaços e caracteres especiais
        val textoLimpo = texto.replace("\\s+".toRegex(), "").uppercase()
        
        // Tentar encontrar placa Mercosul
        val matcherMercosul = placaMercosulPattern.matcher(textoLimpo)
        if (matcherMercosul.find()) {
            return formatarPlaca(matcherMercosul.group())
        }
        
        // Tentar encontrar placa antiga
        val matcherAntiga = placaAntigaPattern.matcher(textoLimpo)
        if (matcherAntiga.find()) {
            return formatarPlaca(matcherAntiga.group())
        }
        
        // Tentar extrair manualmente (7 caracteres: 3 letras + 4 números)
        val linhas = texto.split("\n")
        for (linha in linhas) {
            val limpo = linha.replace("[^A-Z0-9]".toRegex(), "").uppercase()
            if (limpo.length == 7) {
                val letras = limpo.substring(0, 3)
                val numeros = limpo.substring(3, 7)
                if (letras.matches("[A-Z]{3}".toRegex()) && numeros.matches("\\d{4}".toRegex())) {
                    return "$letras-$numeros"
                }
            }
        }
        
        return null
    }

    private fun formatarPlaca(placa: String): String {
        val limpo = placa.replace("-", "").replace(" ", "")
        return if (limpo.length == 7) {
            "${limpo.substring(0, 3)}-${limpo.substring(3)}"
        } else {
            limpo
        }
    }

    fun fechar() {
        recognizer.close()
    }
}

