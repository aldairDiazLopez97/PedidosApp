package com.aplicaciones.pedidosapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.aplicaciones.pedidosapp.databinding.ActivityItemOfListBinding

class ItemOfList : AppCompatActivity() {
    private lateinit var binding: ActivityItemOfListBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityItemOfListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.resBan.setOnClickListener{
            val intent = Intent(this, Banner::class.java)
            startActivity(intent)
        }
    }
}