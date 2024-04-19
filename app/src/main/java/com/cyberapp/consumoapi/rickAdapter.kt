package com.cyberapp.consumoapi

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
//esto es para RecyclerView
//con List<rickItemResponse> = emptyList() hacemos que desde un principio este vacia y no la tengamos que iniciar
class rickAdapter(var rickList: List<RickItemResponse> = emptyList(),
                  private val onItemSelected:(Int) -> Unit //le decimos que recibe una funcion lamda que tenga un parametro int
): RecyclerView.Adapter<rickViewHolder>(){

    //esta funcion es la que constantemente va a actualizar la lista
    fun updateList(list: List<RickItemResponse>){
        rickList = list
        notifyDataSetChanged() //con esto avisdamos que se recargue porque habra cambios
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): rickViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return rickViewHolder(layoutInflater.inflate(R.layout.item_personaje, parent, false))
    }

    override fun getItemCount() = rickList.size

    override fun onBindViewHolder(viewholder: rickViewHolder, position: Int) {
        viewholder.bind(rickList[position], onItemSelected) //lo paso sin llaves, porque con llaves lo ejecuto
    }

}