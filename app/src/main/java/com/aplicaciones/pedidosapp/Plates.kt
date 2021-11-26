package com.aplicaciones.pedidosapp

import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Platillos(val nombre: String? = null,
                     val descripcion: String? = null,
                     val precio: Double? = null,
                     val imagenurl: String? = null,
                     @Exclude val key: String? = null) {

}