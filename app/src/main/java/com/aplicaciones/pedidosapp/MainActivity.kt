package com.aplicaciones.pedidosapp

import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.aplicaciones.pedidosapp.databinding.ActivityMainBinding
import com.bumptech.glide.Glide
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener  {

    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityMainBinding
    private val fileResult = 1

    private lateinit var drawer: DrawerLayout
    private lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // Initialize Firebase Auth
        auth = Firebase.auth

        // Esto si se agrega el boton de cerrar sesion
        binding.signOutImageView.setOnClickListener{
            signOut()
        }
        binding.updateProfileAppCompatButton.setOnClickListener{
            val name = binding.nameEditText.text.toString()
            updateProfile(name)
        }
        binding.profileImageView.setOnClickListener {
            fileManager()
        }
        binding.updatePasswordTextView.setOnClickListener {
            val intent = Intent(this, UpdatePasswordActivity::class.java)
            startActivity(intent)
        }
        binding.deleteAccountTextView.setOnClickListener {
            val intent = Intent(this, DeleteAccountActivity::class.java)
            startActivity(intent)
        }
        binding.listPlates.setOnClickListener {
            val intent = Intent(this, CreateRegister::class.java)
            startActivity(intent)
        }

        val toolbar: androidx.appcompat.widget.Toolbar= findViewById(R.id.toolbar_main)
        setSupportActionBar(toolbar)
        drawer = findViewById(R.id.drawer_layout)
        toggle = ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer.addDrawerListener(toggle)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
        val navigationView: NavigationView = findViewById(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener (this )


        updateUI()
    }

    private fun fileManager() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        startActivityForResult(intent, fileResult)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == fileResult) {
            if (resultCode == RESULT_OK && data != null) {
                val uri = data.data
                uri?.let { imageUpload(it) }
            }
        }
    }

    private fun imageUpload(mUri: Uri) {

        val user = auth.currentUser
        val folder: StorageReference = FirebaseStorage.getInstance().reference.child("Users")
        val fileName: StorageReference = folder.child("img"+user!!.uid)

        fileName.putFile(mUri).addOnSuccessListener {
            fileName.downloadUrl.addOnSuccessListener { uri ->

                val profileUpdates = userProfileChangeRequest {
                    photoUri = Uri.parse(uri.toString())
                }

                user.updateProfile(profileUpdates)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(this, "Se realizaron los cambios correctamente.",
                                Toast.LENGTH_SHORT).show()
                            updateUI()
                        }
                    }
            }
        }.addOnFailureListener {
            Log.i("TAG", "file upload error")
        }
    }

    private fun updateProfile(name: String ){
        val user = auth.currentUser
        val profileUpdates = userProfileChangeRequest {
            displayName = name
        }
        user!!.updateProfile(profileUpdates)
            .addOnCompleteListener{ task->
                if(task.isSuccessful){
                    Toast.makeText(baseContext, "Se realizaron los cambios",
                        Toast.LENGTH_SHORT).show()
                    updateUI()
                }
            }
    }
    // ************
    private fun signOut() {
        Firebase.auth.signOut()
        val intent = Intent(this, SignInActivity::class.java)
        startActivity(intent)
    }
    private fun updateUI() {
        val user = auth.currentUser // Se recuera el usuario logeado
        if (user!=null){
            binding.emailTextView.text = user.email
            binding.nameTextView.text = user.displayName
            binding.nameEditText.setText(user.displayName)
            Glide
                .with(this)
                .load(user.photoUrl)
                .centerCrop()
                .placeholder(R.drawable.profile_photo)
                .into(binding.profileImageView)
            Glide
                .with(this)
                .load(user.photoUrl)
                .centerCrop()
                .placeholder(R.drawable.profile_photo)
                .into(binding.bgProfileImageView)
        }

    }
    // 137 y 52
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