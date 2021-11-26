package com.aplicaciones.pedidosapp

import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.aplicaciones.pedidosapp.databinding.ActivityCarritoBinding
import com.aplicaciones.pedidosapp.databinding.ActivityPlateListBinding
import com.bumptech.glide.Glide
import com.google.android.material.navigation.NavigationView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_carrito.*

class CarritoActivity : AppCompatActivity(),  NavigationView.OnNavigationItemSelectedListener {

    private lateinit var binding: ActivityCarritoBinding

    private lateinit var drawer: DrawerLayout
    private lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_carrito)

        val key = intent.getStringExtra("key")
        val database = Firebase.database
        @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS") val myRef = database.getReference("Platillos").child(
            key.toString()
        )

        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                val Platillos:Platillos? = dataSnapshot.getValue(Platillos::class.java)
                if (Platillos != null) {
                    nombre.text = Platillos.nombre.toString()
                    descripcion.text = Platillos.descripcion.toString()
                    precio.text = Platillos.precio.toString()
                    images(Platillos.imagenurl.toString())
                }
            }
            override fun onCancelled(error: DatabaseError) {
                Log.w("TAG", "Failed to read value.", error.toException())
            }
        })

    }
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.nav_item_home -> abrirHome()
            R.id.nav_item_platillos -> abrirPlatillos()
            R.id.nav_item_perfil -> abrirPerfil()
        }
        drawer.closeDrawer(GravityCompat.START)
        return true
    }

    private fun abrirPerfil() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    private fun abrirPlatillos() {
        val intent = Intent(this, PlateList::class.java)
        startActivity(intent)
    }

    private fun abrirHome() {
        val intent = Intent(this, Banner::class.java)
        startActivity(intent)
    }

    override fun onPostCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onPostCreate(savedInstanceState)
        toggle.syncState()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        toggle.onConfigurationChanged(newConfig)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(toggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }
    private  fun images(imagenurl: String){
        Glide.with(this)
            .load(imagenurl)
            .into(imagenurlP)

    }
}