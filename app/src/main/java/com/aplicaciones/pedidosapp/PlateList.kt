package com.aplicaciones.pedidosapp

import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.aplicaciones.pedidosapp.databinding.ActivityPlateListBinding
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_plate_list.*

class PlateList : AppCompatActivity() , NavigationView.OnNavigationItemSelectedListener{
    private lateinit var binding: ActivityPlateListBinding

    private lateinit var drawer: DrawerLayout
    private lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPlateListBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val plate = Plates(R.drawable.frejolada ,"Frejolada","Lo mas deliciosos en comida criolla","Criollo",20.00)
        val plate1 = Plates(R.drawable.ceviche ,"Ceviche","Lo mas deliciosos en mariscos","Mariscos",30.00)
        val plate2 = Plates(R.drawable.jugoso ,"Jugoso","Lo mas deliciosos en mariscos","Mariscos",35.00)
        val plate3 = Plates(R.drawable.cuyada ,"Cuyada","Lo mas deliciosos en comida criolla","Criollo",18.00)
        val plate4 = Plates(R.drawable.lomo ,"Lomo Saltado","Lo mas deliciosos en menus","Menu",12.00)

        val listPlates = listOf(plate,plate1,plate2,plate3,plate4)
        val adapt = PlateAdapter(this,listPlates)

        listp.adapter= adapt


        val toolbar: androidx.appcompat.widget.Toolbar= findViewById(R.id.toolbar_main)
        setSupportActionBar(toolbar)
        drawer = findViewById(R.id.drawer_layout)
        toggle = ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer.addDrawerListener(toggle)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
        val navigationView: NavigationView = findViewById(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener (this )

        binding.resBan.setOnClickListener{
            val intent = Intent(this, Banner::class.java)
            startActivity(intent)
        }

    }
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.nav_item_home -> abrirHome()
            R.id.nav_item_platillos -> abrirPlatillos()
            R.id.nav_item_carrito -> abrirCarrito()
            R.id.nav_item_perfil -> abrirPerfil()
        }
        drawer.closeDrawer(GravityCompat.START)
        return true
    }

    private fun abrirPerfil() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    private fun abrirCarrito() {
        val intent = Intent(this, CarritoActivity::class.java)
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
}