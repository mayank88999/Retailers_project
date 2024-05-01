package com.example.retailers_project

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.example.retailers_project.databinding.ActivityRegisterBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class Register : AppCompatActivity() {
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private lateinit var mAuth: FirebaseAuth
    private val fstore = Firebase.firestore
    private lateinit var binding: ActivityRegisterBinding
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_shops -> {
                val intent=Intent(this@Register,disctict::class.java)
                startActivity(intent)
                finish()
                true
            }
            R.id.action_logout -> {
                mAuth.signOut()
                val intent=Intent(this@Register,Signup::class.java)
                startActivity(intent)
                finish()
                true
            }
            R.id.action_register -> {
                val intent=Intent(this@Register,shopdetails::class.java)
                startActivity(intent)
                finish()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        mAuth = FirebaseAuth.getInstance()

        binding.apply {
            mAuth.currentUser?.let { user ->
                val userName = user.displayName
                val email = user.email
                name.text = "Welcome, $userName"

                val districts = resources.getStringArray(R.array.districts_array)
                val adapter = ArrayAdapter(this@Register, android.R.layout.simple_spinner_item, districts)
                spinner.adapter = adapter
                spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                        // Handle item selection if needed
                    }

                    override fun onNothingSelected(parent: AdapterView<*>?) {
                        // Handle case where nothing is selected
                    }
                }

                nextButton.setOnClickListener {
                    val shopname = editTextShopName.text.toString()
                    val phone = editTextPhone.text.toString()
                    val district = spinner.selectedItem.toString()
                    val description = getSharedPreferences("PREF_NAME", MODE_PRIVATE)
                        .getString("Description", "defaultString").toString()
                    val address = getSharedPreferences("PREF_NAME", MODE_PRIVATE)
                        .getString("Address", "defaultString").toString()
                    val intent=intent
                    val type = intent.getStringExtra("Type")

                    if (shopname.isNotEmpty() && phone.isNotEmpty()) {
                        val details = hashMapOf(
                            "Shop_type" to type,
                            "Name" to userName,
                            "Email" to email,
                            "Shopname" to shopname,
                            "phone" to phone,
                            "uid" to user.uid,
                            "Description" to description,
                            "Address" to address
                        )
                        fstore.collection(district).document(user.uid)
                            .set(details)
                            .addOnSuccessListener {
                                Log.d("RegisterActivity", "DocumentSnapshot added with ID:")
                                startActivity(Intent(this@Register, MainActivity::class.java).apply {
                                    putExtra("Details", email)
                                })
                            }
                            .addOnFailureListener { e ->
                                Log.w("RegisterActivity", "Error adding document", e)
                                Toast.makeText(this@Register, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                            }
                    } else {
                        Toast.makeText(this@Register, "Please fill all fields", Toast.LENGTH_SHORT).show()
                    }
                }
            } ?: run {
                startActivity(Intent(this@Register, Signup::class.java))
                finish()
            }
        }
    }
}