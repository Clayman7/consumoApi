package com.cyberapp.consumoapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.content.ContextCompat
import com.cyberapp.consumoapi.databinding.ActivityDetailBinding
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class DetailActivity : AppCompatActivity() {
    companion object{ // EN UN companion object SE PUEDE ACCEDER A LO QUE HAYA DENTRO DE LAS LLAVES EN OTRAS PANTALLAS Y MAS COSAS
        const val EXTRA_ID = "extra_id"
        const val DEFAULT_VALUE = 0
    }

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //val id: String = intent.getStringExtra(EXTRA_ID).orEmpty()
        val id = intent.getIntExtra(EXTRA_ID, DEFAULT_VALUE)
        getPersonajeInformation(id)
        Log.i("AQUIIIIIII", id.toString())

    }

    private fun getPersonajeInformation(id: Int){
        CoroutineScope(Dispatchers.IO).launch {
            val personajeDetail =  getRetrofit().create(ApiService::class.java).getPersonajeDetail(id)
            if (personajeDetail.body() != null){
                Log.i("cyberApp", personajeDetail.body().toString())
                runOnUiThread{
                    createUI(personajeDetail.body()!!) //con body() tengo toda la info y con !! le digo que estoy seguro que no serta nulo porque yo ya lo comprobe con un if
                }
            }
        }
    }

    private fun createUI(personaje: personajeDetailResponse){
        Picasso.get().load(personaje.image).into(binding.ivImage)
        binding.tvPersonajeName.text = personaje.name
        binding.tvPersonajeGenero.text = personaje.gender
        binding.tvPersonajeSpecies.text = personaje.species
        binding.tvPersonajeStatus.text = personaje.status
        if (personaje.status == "Alive"){
            val color = ContextCompat.getColor(this, R.color.vivo)
            binding.tvPersonajeStatus.setTextColor(color)
        }
        if (personaje.status == "unknown"){
            val color = ContextCompat.getColor(this, R.color.desconocido)
            binding.tvPersonajeStatus.setTextColor(color)
        }
        if (personaje.status == "Dead"){
            val color = ContextCompat.getColor(this, R.color.muerto)
            binding.tvPersonajeStatus.setTextColor(color)
        }
    }

    private fun getRetrofit(): Retrofit {
        val retrofit = Retrofit
            .Builder()
            .baseUrl("https://rickandmortyapi.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit
    }
}