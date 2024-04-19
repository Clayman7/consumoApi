package com.cyberapp.consumoapi

import com.google.gson.annotations.SerializedName

//data class personajeinicioResponse(@SerializedName("results") val results: List<personajeDetailResponse>)

data class personajeDetailResponse(@SerializedName("name") val name: String,
                                   @SerializedName("status") val status: String,
                                   @SerializedName("species") val species: String,
                                   @SerializedName("gender") val gender: String,
                                   @SerializedName("image") val image: String)