package com.aplicaciones.pedidosapp


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_create_register.*



class CreateRegister : AppCompatActivity() {

    private val database = Firebase.database

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_register)

        val myRef = database.getReference("Platillos")

        val nombre=nombreEdit.text
        val descripcion=descripcionEdit.text
        val imagenurl=imagenurlEdit.text
        val precio=precioEdit.text


        addbuttom.setOnClickListener { v ->
            val Platillos = Platillos(nombre.toString(), descripcion.toString(), precio.toString(), imagenurl.toString())
            myRef.child(myRef.push().key.toString()).setValue(Platillos)
            finish()
        }
    }



}

