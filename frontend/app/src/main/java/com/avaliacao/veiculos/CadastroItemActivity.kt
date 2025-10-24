package com.avaliacao.veiculos

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.appbar.MaterialToolbar

class CadastroItemActivity : AppCompatActivity() {

    private lateinit var toolbar: MaterialToolbar
    private lateinit var etNome: EditText
    private lateinit var etCategoria: EditText
    private lateinit var etValorPadrao: EditText
    private lateinit var etDescricao: EditText
    private lateinit var btnSalvar: Button
    private lateinit var btnCancelar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro_item)

        setupViews()
        setupListeners()
    }

    private fun setupViews() {
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        etNome = findViewById(R.id.etNome)
        etCategoria = findViewById(R.id.etCategoria)
        etValorPadrao = findViewById(R.id.etValorPadrao)
        etDescricao = findViewById(R.id.etDescricao)
        btnSalvar = findViewById(R.id.btnSalvar)
        btnCancelar = findViewById(R.id.btnCancelar)
    }

    private fun setupListeners() {
        btnSalvar.setOnClickListener {
            salvarItem()
        }

        btnCancelar.setOnClickListener {
            finish()
        }
    }

    private fun salvarItem() {
        val nome = etNome.text.toString().trim()
        val categoria = etCategoria.text.toString().trim()
        val valorPadrao = etValorPadrao.text.toString().trim()
        val descricao = etDescricao.text.toString().trim()

        if (nome.isEmpty()) {
            Toast.makeText(this, R.string.campo_obrigatorio, Toast.LENGTH_SHORT).show()
            return
        }

        // TODO: Implementar chamada à API para salvar item
        Toast.makeText(this, R.string.sucesso_salvar, Toast.LENGTH_SHORT).show()
        finish()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}

