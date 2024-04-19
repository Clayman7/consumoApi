package com.cyberapp.consumoapi

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.cyberapp.consumoapi.databinding.ItemPersonajeBinding
import com.squareup.picasso.Picasso

//esto es para RecyclerView
class rickViewHolder(view:View): RecyclerView.ViewHolder(view){

    private val binding = ItemPersonajeBinding.bind(view)
    fun bind(rickItemResponse: RickItemResponse, onItemSelected:(Int) -> Unit){ //onItemSelected:(Int) aqui le decimos que lo que reciba de su funcion lamda debe de ser int
        binding.tvPersonajeName.text = rickItemResponse.name
        binding.ivPersonaje
       // binding.tvPersonajeStatus.text = rickItemResponse.status
        //binding.tvPersonajeSpecies.text = rickItemResponse.species
        //binding.tvPersonaGender.text = rickItemResponse.gender

        Picasso.get().load(rickItemResponse.image).into(binding.ivPersonaje) //esta libreria trae la imagen
        //en into(binding.ivPersonaje) le decimos donde queremos que lo cargue
        //para manejar la imagen se tiene scaletype para diferentes tamaños  se explica a fondo en el min 1:41
        binding.root.setOnClickListener { onItemSelected(rickItemResponse.personajeId) } //aqui recibe un int, pero si quisiera un string basta que haga un to_String()
        //root es para decirle que es toda la vista, opsea que se activara el setonclicklistener cada que alguien presione en cualquier parte de la vista
    }
}