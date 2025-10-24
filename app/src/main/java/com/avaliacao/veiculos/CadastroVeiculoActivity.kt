package com.avaliacao.veiculos

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.avaliacao.veiculos.database.AppDatabase
import com.avaliacao.veiculos.database.VeiculoEntity
import com.avaliacao.veiculos.repository.VeiculoRepository
import com.google.android.material.appbar.MaterialToolbar
import kotlinx.coroutines.launch

class CadastroVeiculoActivity : AppCompatActivity() {

    private lateinit var toolbar: MaterialToolbar
    private lateinit var etPlaca: EditText
    private lateinit var etModelo: EditText
    private lateinit var etAno: EditText
    private lateinit var btnCapturarPlaca: Button
    private lateinit var btnSalvar: Button
    private lateinit var btnCancelar: Button
    
    private lateinit var veiculoRepository: VeiculoRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro_veiculo)

        // Inicializar repositório
        val database = AppDatabase.getDatabase(applicationContext)
        veiculoRepository = VeiculoRepository(database.veiculoDao())

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
        val placa = etPlaca.text.toString().trim().uppercase()
        val modelo = etModelo.text.toString().trim()
        val anoStr = etAno.text.toString().trim()

        if (placa.isEmpty() || modelo.isEmpty() || anoStr.isEmpty()) {
            Toast.makeText(this, R.string.campo_obrigatorio, Toast.LENGTH_SHORT).show()
            return
        }

        val ano = anoStr.toIntOrNull()
        if (ano == null || ano < 1900 || ano > 2100) {
            Toast.makeText(this, "Ano inválido", Toast.LENGTH_SHORT).show()
            return
        }

        lifecycleScope.launch {
            try {
                // Verificar se placa já existe
                val veiculoExistente = veiculoRepository.getVeiculoByPlaca(placa)
                if (veiculoExistente != null) {
                    runOnUiThread {
                        Toast.makeText(
                            this@CadastroVeiculoActivity,
                            "Veículo com esta placa já existe",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    return@launch
                }

                // Inserir novo veículo
                val veiculo = VeiculoEntity(
                    placa = placa,
                    modelo = modelo,
                    ano = ano
                )
                
                veiculoRepository.insertVeiculo(veiculo)
                
                runOnUiThread {
                    Toast.makeText(
                        this@CadastroVeiculoActivity,
                        R.string.sucesso_salvar,
                        Toast.LENGTH_SHORT
                    ).show()
                    finish()
                }
            } catch (e: Exception) {
                runOnUiThread {
                    Toast.makeText(
                        this@CadastroVeiculoActivity,
                        "Erro ao salvar: ${e.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
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

