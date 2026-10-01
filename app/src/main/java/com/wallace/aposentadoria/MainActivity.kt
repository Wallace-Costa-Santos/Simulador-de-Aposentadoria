package com.wallace.aposentadoria

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Referências das views
        val spinnerGenero = findViewById<Spinner>(R.id.spinnergenero)
        val editIdade = findViewById<EditText>(R.id.edIdade)
        val btnCalcular = findViewById<Button>(R.id.button)
        val txtResultado = findViewById<TextView>(R.id.txtResultado)

        // Carrega o array 'opcoes_genero' criado no strings.xml
        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.opcoes_genero,
            android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerGenero.adapter = adapter

        btnCalcular.setOnClickListener {
            val idadeTexto = editIdade.text.toString()

            if (idadeTexto.isEmpty()) {
                editIdade.error = "Digite sua idade"
                return@setOnClickListener
            }

            val idade = idadeTexto.toIntOrNull()
            if (idade == null) {
                editIdade.error = "Idade inválida"
                return@setOnClickListener
            }

            val generoSelecionado = spinnerGenero.selectedItem.toString()

            // Define a idade de aposentadoria com base no gênero selecionado
            val idadeAposentadoria = if (generoSelecionado == "Masculino") 65 else 62

            if (idade >= idadeAposentadoria) {
                txtResultado.text = "Você já pode se aposentar!"
            } else {
                val anosRestantes = idadeAposentadoria - idade
                txtResultado.text = "Faltam $anosRestantes anos para você se aposentar."
            }
        }
    }
}