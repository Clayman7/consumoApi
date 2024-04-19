package com.cyberapp.consumoapi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.cyberapp.consumoapi.DetailActivity.Companion.EXTRA_ID
import com.cyberapp.consumoapi.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var retrofit: Retrofit

    private lateinit var adapter: rickAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        retrofit = getRetrofit()
        initUI()
    }

    private fun initUI() {
        // el setOnQueryTextListener es como el setOnClickListener pero es el que necesitamos para la barra de busqueda
        binding.searchView.setOnQueryTextListener( object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean { // esta la funcion que se llamara cuando pulsemos el boton de buscar
                searchByName(query.orEmpty())
                return false
            }

            override fun onQueryTextChange(newText: String?) = false

        })

        //esto es parte del recyclerview
        adapter = rickAdapter{personajeId -> navigateToDetail(personajeId)}
        binding.rvPersonajes.setHasFixedSize(true)
        binding.rvPersonajes.layoutManager = LinearLayoutManager(this)
        binding.rvPersonajes.adapter = adapter
    }

    private fun searchByName(query: String){
        binding.progresbar.isVisible = true //si lo pongo en false es como si lo pusiera en gone y no en invisible, algo a tomar en cuenta, hacer invisible no siempre es bueno

        CoroutineScope(Dispatchers.IO).launch {
            val myResponse = retrofit.create(ApiService::class.java).getPersonajes(query)
            if (myResponse.isSuccessful){
                Log.i("cyberApp", "funciona")
                val response: RickDataResponse? = myResponse.body() //dentro de body() es donde esta todo
                if(response != null){
                    Log.i("cyberApp", response.toString())
                    runOnUiThread{
                        // runOnUiThread con esto le decimos que corra en el hilo principal lo que este entre llavers
                        adapter.updateList(response.character) // esto es lo que va a llamar a la funcion que lo actuasliza
                        binding.progresbar.isVisible = false
                    }
                }

                
            }else{
                Log.i("cyberApp", "No funciona")
            }
        }
    }

    private fun getRetrofit(): Retrofit{
        val retrofit = Retrofit
            .Builder()
            .baseUrl("https://rickandmortyapi.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit
    }

    private fun navigateToDetail(id: Int){
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra( EXTRA_ID, id) //de esta forma le estamos pasando una variable al activity que se va a crear, al decirle EXTRA_ID le ponemos por nombre una constnate string, esto lo hacemos para equivocarnos de nombre
        startActivity(intent)
    }
}