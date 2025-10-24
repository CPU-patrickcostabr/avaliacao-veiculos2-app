package com.avaliacao.veiculos

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.appbar.MaterialToolbar

class CadastroVeiculoActivity : AppCompatActivity() {

    private lateinit var toolbar: MaterialToolbar
    private lateinit var etPlaca: EditText
    private lateinit var etModelo: EditText
    private lateinit var etAno: EditText
    private lateinit var btnCapturarPlaca: Button
    private lateinit var btnSalvar: Button
    private lateinit var btnCancelar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro_veiculo)

        setupViews()
        setupListeners()
    }

    private fun setupViews() {
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        etPlaca = findViewById(R.id.etPlaca)
        etModelo = findViewById(R.id.etModelo)
        etAno = findViewById(R.id.etAno)
        btnCapturarPlaca = findViewById(R.id.btnCapturarPlaca)
        btnSalvar = findViewById(R.id.btnSalvar)
        btnCancelar = findViewById(R.id.btnCancelar)
    }

    private fun setupListeners() {
        btnCapturarPlaca.setOnClickListener {
            // Abrir câmera para capturar placa
            val intent = Intent(this, CameraActivity::class.java)
            intent.putExtra("tipo", "placa")
            startActivityForResult(intent, REQUEST_CAMERA_PLACA)
        }

        btnSalvar.setOnClickListener {
            salvarVeiculo()
        }

        btnCancelar.setOnClickListener {
            finish()
        }
    }

    private fun salvarVeiculo() {
        val placa = etPlaca.text.toString().trim()
        val modelo = etModelo.text.toString().trim()
        val ano = etAno.text.toString().trim()

        if (placa.isEmpty() || modelo.isEmpty() || ano.isEmpty()) {
            Toast.makeText(this, R.string.campo_obrigatorio, Toast.LENGTH_SHORT).show()
            return
        }

        // TODO: Implementar chamada à API para salvar veículo
        Toast.makeText(this, R.string.sucesso_salvar, Toast.LENGTH_SHORT).show()
        finish()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CAMERA_PLACA && resultCode == RESULT_OK) {
            val placaCapturada = data?.getStringExtra("placa")
            if (!placaCapturada.isNullOrEmpty()) {
                etPlaca.setText(placaCapturada)
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    companion object {
        private const val REQUEST_CAMERA_PLACA = 100
    }
}

