package com.aplicaciones.pedidosapp

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.isDigitsOnly
import com.aplicaciones.pedidosapp.databinding.ActivityCreateRegisterBinding
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_create_register.*


class CreateRegister : AppCompatActivity() {

    private lateinit var binding: ActivityCreateRegisterBinding
    private lateinit var database : DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val listTy = resources.getStringArray(R.array.opciones)
        val categoria = ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,listTy)

        binding.Tipo.adapter=categoria


        binding.tvRegister.setOnClickListener{
            val nombre= binding.Nombre.text.toString()
            val descripcion= binding.Descripcion.text.toString()
            val precio= binding.Precio.text.toString().toDouble()
            binding.Tipo.onItemSelectedListener = object:
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    Toast.makeText(this@CreateRegister,"Registro Exitoso",Toast.LENGTH_LONG).show()
                    val tipo = listTy[position]
                    database=FirebaseDatabase.getInstance().getReference("Platillos")
                    val Platillos= Plate(nombre,descripcion,precio,tipo)
                    database.child(nombre).setValue(Platillos).addOnSuccessListener {
                        binding.Nombre.text?.trim()
                        binding.Descripcion.text?.trim()
                        binding.Precio.text?.trim()
                    }
                }
                override fun onNothingSelected(p0: AdapterView<*>?) {
                    Toast.makeText(this@CreateRegister,"No se completo el registro",Toast.LENGTH_LONG).show()

                }
            }



        }
    }

}

