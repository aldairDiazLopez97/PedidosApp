package com.aplicaciones.pedidosapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_plate_list.*

class PlateList : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_plate_list)
        val plate = Plate(R.drawable.frejolada ,"Frejolada","Lo mas deliciosos en comida criolla","Criollo",20.00)
        val plate1 = Plate(R.drawable.ceviche ,"Ceviche","Lo mas deliciosos en mariscos","Mariscos",30.00)
        val plate2 = Plate(R.drawable.jugoso ,"Jugoso","Lo mas deliciosos en mariscos","Mariscos",35.00)
        val plate3 = Plate(R.drawable.cuyada ,"Cuyada","Lo mas deliciosos en comida criolla","Criollo",18.00)
        val plate4 = Plate(R.drawable.lomo ,"Lomo Saltado","Lo mas deliciosos en menus","Menu",12.00)

        val listPlates = listOf(plate,plate1,plate2,plate3,plate4)
        val adapt = PlateAdapter(this,listPlates)

        listp.adapter= adapt

    }
}