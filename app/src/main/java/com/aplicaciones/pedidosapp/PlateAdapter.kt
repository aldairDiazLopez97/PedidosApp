package com.aplicaciones.pedidosapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_item_of_list.view.*
import kotlinx.android.synthetic.main.activity_sign_in.view.*

class PlateAdapter(private val mContext:Context, private val listaPlatos: List<Plate>): ArrayAdapter<Plate>(mContext,0,listaPlatos){
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val layout = LayoutInflater.from(mContext).inflate(R.layout.activity_item_of_list,parent,false)

        val platos = listaPlatos[position]

        layout.nombre.text = platos.nombre
        layout.descripcion.text = platos.descripcion
        layout.precio.text= "$${platos.precio}"
        layout.imageView2.setImageResource(platos.imagen)

        return layout
    }
}