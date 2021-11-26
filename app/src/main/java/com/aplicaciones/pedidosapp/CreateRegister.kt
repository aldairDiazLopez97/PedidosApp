package com.aplicaciones.pedidosapp


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.activity_create_register.*



class CreateRegister : AppCompatActivity() {

    private val database = Firebase.database
    private val myRef = database.getReference("Platillos")
    private val file = 1
    private var imagenurl: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_register)



        addbuttom.setOnClickListener {

            val nombre: String = nombreEdit.text.toString()
            val descripcion: String = descripcionEdit.text.toString()
            val precio: String = precioEdit.text.toString()
            val key: String = myRef.push().key.toString()
            val folder: StorageReference = FirebaseStorage.getInstance().reference.child("game")
            val Platillos: StorageReference = folder.child("img$key")

            if (imagenurl == null) {
                val Platillos = Platillos(nombre, descripcion, precio.toDouble())
                myRef.child(key).setValue(Platillos)
            } else {
                Platillos.putFile(imagenurl!!).addOnSuccessListener {
                    Platillos.downloadUrl.addOnSuccessListener { uri ->
                        val mVideoGame = Platillos(nombre, descripcion, precio.toDouble(), uri.toString())
                        myRef.child(key).setValue(mVideoGame)
                    }
                }
            }

            finish()
        }
        imageView.setOnClickListener {
            fileUpload()
        }
    }
    private fun fileUpload() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "*/*"
        startActivityForResult(intent, file)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == file) {
            if (resultCode == RESULT_OK) {
                imagenurl = data!!.data
                imageView.setImageURI(imagenurl)
            }
        }
    }



}

