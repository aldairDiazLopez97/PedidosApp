package com.aplicaciones.pedidosapp

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.aplicaciones.pedidosapp.databinding.ActivityCreateRegisterBinding
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_create_register.*


class CreateRegister : AppCompatActivity() {

    private lateinit var binding: ActivityCreateRegisterBinding
    private lateinit var database : DatabaseReference
    private val SELECT_ACTIVITY = 50
    private var imageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityCreateRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val listTy = resources.getStringArray(R.array.opciones)

        val categoria = ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,listTy)


        binding.Tipo.adapter=categoria

        binding.img.setOnClickListener {
            ImageController.selectPhoto(this, SELECT_ACTIVITY)
        }

        binding.tvRegister.setOnClickListener{
            val nombre= binding.Nombre.text.toString()
            val descripcion= binding.Descripcion.text.toString()
            val precio= binding.Precio.text.toString().toDouble()
            var tipo = binding.Tipo.adapter.toString()

            binding.img.setImageURI(imageUri)
            binding.Tipo.onItemSelectedListener = object:
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    tipo = listTy[position].toString()
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                    TODO("Not yet implemented")
                }

            }
            database=FirebaseDatabase.getInstance().getReference("Platillos")
            val Platillos= Plate(nombre,descripcion,precio,tipo)
            database.child(nombre).setValue(Platillos).addOnSuccessListener {
                binding.Nombre.text?.clear()
                binding.Descripcion.text?.clear()
                binding.Precio.text?.clear()
            }



            /**/

        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when {
            requestCode == SELECT_ACTIVITY && resultCode == Activity.RESULT_OK -> {
                imageUri = data!!.data

                binding.img.setImageURI(imageUri)
            }
        }
    }


}

