package com.cyberapp.consumoapi

import com.google.gson.annotations.SerializedName

data class RickDataResponse(@SerializedName("results") val character: List<RickItemResponse>) //haga que la list sea de rickItemResponse para que en esa meta todos los hijos o subniveles que yo quiera

data class  RickItemResponse(
    @SerializedName("id") val personajeId: Int,
    @SerializedName("name") val name: String,
    @SerializedName("image") val image: String)

//data class RickImageResponse(@SerializedName())
