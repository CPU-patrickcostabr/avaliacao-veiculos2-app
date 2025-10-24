package com.avaliacao.veiculos

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.appbar.MaterialToolbar

class MainActivity : AppCompatActivity() {

    private lateinit var toolbar: MaterialToolbar
    private lateinit var recyclerView: RecyclerView
    private lateinit var fabNovaAvaliacao: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupViews()
        setupRecyclerView()
        setupListeners()
    }

    private fun setupViews() {
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        
        recyclerView = findViewById(R.id.recyclerViewAvaliacoes)
        fabNovaAvaliacao = findViewById(R.id.fabNovaAvaliacao)
    }

    private fun setupRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        // Adapter será implementado posteriormente
    }

    private fun setupListeners() {
        fabNovaAvaliacao.setOnClickListener {
            val intent = Intent(this, NovaAvaliacaoActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_cadastro_item -> {
                startActivity(Intent(this, CadastroItemActivity::class.java))
                true
            }
            R.id.menu_cadastro_fornecedor -> {
                startActivity(Intent(this, CadastroFornecedorActivity::class.java))
                true
            }
            R.id.menu_cadastro_veiculo -> {
                startActivity(Intent(this, CadastroVeiculoActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}

