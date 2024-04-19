package com.cyberapp.consumoapi

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("character/")
    suspend fun  getPersonajes(@Query("name") nom : String):Response<RickDataResponse>
    //En este caso, he cambiado @Path("nom") a @Query("name"). Esto se debe a que la URL espera un parámetro de consulta (?name=valor) en lugar de un parámetro de ruta (/valor). Con @Query, Retrofit agregará el parámetro a la URL correctamente.
    // esto lo cambie en lugar de haber hecho
    // @GET("character/?name={nom}")
    // suspend fun  getPersonajes(@Path("nom") nom : String):Response<RickDataResponse>


    @GET("character/{id}")
    suspend fun getPersonajeDetail(@Path("id") personajeId: Int):Response<personajeDetailResponse>

    //character/2
}