package com.aplicaciones.pedidosapp

import android.net.Uri
import android.widget.SpinnerAdapter


class Plate(
    val nombre:String?=null,
    val descripcion:String?=null,
    val precio:Double?=null,
    val tipo: String? = null,
    val imagen: Uri? =null){
}