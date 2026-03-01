package com.example.gethttp

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import java.io.IOException

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

        val editCep = findViewById<TextInputEditText>(R.id.editCep)
        val btnBuscar = findViewById<MaterialButton>(R.id.btnBuscar)
        val progressBar = findViewById<ProgressBar>(R.id.progressBar)
        val cardResultado = findViewById<CardView>(R.id.cardResultado)
        val cardErro = findViewById<CardView>(R.id.cardErro)
        val tvCep = findViewById<TextView>(R.id.tvCep)
        val tvRua = findViewById<TextView>(R.id.tvRua)
        val tvBairro = findViewById<TextView>(R.id.tvBairro)
        val tvCidade = findViewById<TextView>(R.id.tvCidade)
        val tvEstado = findViewById<TextView>(R.id.tvEstado)
        val tvCoordenadas = findViewById<TextView>(R.id.tvCoordenadas)
        val tvErro = findViewById<TextView>(R.id.tvErro)

        btnBuscar.setOnClickListener {
            val cepDigitado = editCep.text?.toString()?.trim() ?: ""

            if (cepDigitado.length != 8) {
                cardErro.visibility = View.VISIBLE
                cardResultado.visibility = View.GONE
                tvErro.text = "Digite um CEP válido com 8 dígitos."
                return@setOnClickListener
            }

            // Exibe loading, esconde cards
            progressBar.visibility = View.VISIBLE
            cardResultado.visibility = View.GONE
            cardErro.visibility = View.GONE

            val client = OkHttpClient()
            val request = Request.Builder()
                .url("https://brasilapi.com.br/api/cep/v2/$cepDigitado")
                .build()

            client.newCall(request).enqueue(object : okhttp3.Callback {

                override fun onFailure(call: okhttp3.Call, e: IOException) {
                    runOnUiThread {
                        progressBar.visibility = View.GONE
                        cardErro.visibility = View.VISIBLE
                        tvErro.text = "Falha na conexão. Verifique sua internet."
                    }
                }

                override fun onResponse(call: okhttp3.Call, response: okhttp3.Response) {
                    val resposta = response.body?.string()

                    runOnUiThread {
                        progressBar.visibility = View.GONE

                        if (!response.isSuccessful || resposta == null) {
                            cardErro.visibility = View.VISIBLE
                            tvErro.text = "CEP não encontrado."
                            return@runOnUiThread
                        }

                        val json = JSONObject(resposta)

                        val cep = json.optString("cep", "-")
                        val state = json.optString("state", "-")
                        val city = json.optString("city", "-")
                        val neighborhood = json.optString("neighborhood", "-")
                        val street = json.optString("street", "-")

                        val location = json.optJSONObject("location")
                        val coordinates = location?.optJSONObject("coordinates")
                        val latitude = coordinates?.optString("latitude") ?: "-"
                        val longitude = coordinates?.optString("longitude") ?: "-"

                        tvCep.text = cep
                        tvRua.text = street
                        tvBairro.text = neighborhood
                        tvCidade.text = city
                        tvEstado.text = state
                        tvCoordenadas.text = "Lat: $latitude\nLng: $longitude"

                        cardResultado.visibility = View.VISIBLE
                    }
                }
            })
        }
    }
}