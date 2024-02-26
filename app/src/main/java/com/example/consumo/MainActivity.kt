package com.example.consumo

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.consumo.apipokemon.PokemonApiService
import com.example.consumo.databinding.ActivityMainBinding
import com.example.consumo.model.Pokemon
import com.example.consumo.model.PokemonRespuesta
import retrofit2.Call
import retrofit2.Callback


import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val text=binding.textpokemon
        val retrofit = Retrofit.Builder()
            .baseUrl("https://pokeapi.co/api/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(PokemonApiService::class.java)
        val call = service.getPokemonById()

        call.enqueue(object : Callback<PokemonRespuesta> {
            override fun onResponse(call: Call<PokemonRespuesta>, response: Response<PokemonRespuesta>) =
                if (response.isSuccessful) {
                    val pokemon: PokemonRespuesta? = response.body()
                    if (pokemon != null) {
                        Toast.makeText(this@MainActivity, "El pokemon consumido es $pokemon", Toast.LENGTH_SHORT).show()
                        text.text = pokemon.toString()
                    } else {

                        Toast.makeText(this@MainActivity, "No se pudo obtener información del pokemon", Toast.LENGTH_SHORT).show()
                    }
                } else {

                    Toast.makeText(this@MainActivity, "Error al consumir el servicio", Toast.LENGTH_SHORT).show()
                }



            override fun onFailure(call: Call<PokemonRespuesta>, t: Throwable) {

                Toast.makeText(this@MainActivity, "Error en la conexión", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
