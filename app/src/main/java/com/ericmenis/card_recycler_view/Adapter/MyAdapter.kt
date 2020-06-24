package com.ericmenis.card_recycler_view.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.AppCompatDrawableManager.get
import androidx.recyclerview.widget.RecyclerView
import com.ericmenis.card_recycler_view.Activitie.SecondActivity
import com.ericmenis.card_recycler_view.Model.Personaje
import com.ericmenis.card_recycler_view.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.row.view.*
import java.lang.reflect.Array.get

class MyAdapter(val personajedbz: ArrayList<Personaje>, val context: Context):
    RecyclerView.Adapter<MyAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        val MESSAGE_MAX_LENGHT: Int = 80

        fun bind(personaje: Personaje){

            itemView.nameDBZ.text = personaje.titledbz

            itemView.imageView.setImageResource(personaje.imgdbz)

            var shortDesc: String
            if (personaje.descripciondbz.length > MESSAGE_MAX_LENGHT){
                shortDesc = personaje.descripciondbz.substring(0, MESSAGE_MAX_LENGHT)+"..."
            }else{
                shortDesc = personaje.descripciondbz
            }

            itemView.descriptionDBZ.text = shortDesc
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.row, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return personajedbz.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(personajedbz[position])
        holder.itemView.setOnClickListener {
            //Toast.makeText(context, "clicked on " + personajedbz[position].titledbz, Toast.LENGTH_SHORT).show()

            val personaje = personajedbz[position]
            val gName: String = personaje.titledbz
            val gImg: Int = personaje.imgdbz
            val gDesc: String = personaje.descripciondbz

            //Intent
            val intent = Intent(context, SecondActivity::class.java)
            intent.putExtra("name", gName)
            intent.putExtra("image", gImg)
            intent.putExtra("description", gDesc)

            context.startActivity(intent)
        }
    }

}