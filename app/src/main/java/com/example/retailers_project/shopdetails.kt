package com.example.retailers_project

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.example.retailers_project.databinding.ActivityShopdetailsBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
class shopdetails : AppCompatActivity(){
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private lateinit var mAuth: FirebaseAuth
    private lateinit var binding: ActivityShopdetailsBinding
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_shops -> {
                val intent=Intent(this@shopdetails,disctict::class.java)
                startActivity(intent)
                finish()
                true
            }
            R.id.action_logout -> {
                mAuth.signOut()
                val intent=Intent(this@shopdetails,Signup::class.java)
                startActivity(intent)
                finish()
                true
            }
            R.id.action_register -> {
                val intent=Intent(this@shopdetails,shopdetails::class.java)
                startActivity(intent)
                finish()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShopdetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        mAuth = FirebaseAuth.getInstance()


        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)


        val textView = findViewById<TextView>(R.id.name1)

        val auth = Firebase.auth
        val user = auth.currentUser
        val spinner = findViewById<Spinner>(R.id.spinner2)
        if (user != null) {
            val userName = user.displayName
            textView.text = "Welcome, " + userName

            val shoptypes = resources.getStringArray(R.array.Shop_type)
            val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, shoptypes)
            spinner.adapter = adapter
            spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    // Handle item selection if needed
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    // Handle case where nothing is selected
                }
            }
            binding.btnnext.setOnClickListener {
                val discription = binding.editTextdiscription.text.toString()
                val address = binding.editTextaddress.text.toString()
                val selectedShopType = spinner.selectedItem.toString()
                if(address!=null&&selectedShopType!=null&&discription!=null){
                    val sh:SharedPreferences=getSharedPreferences("PREF_NAME",MODE_PRIVATE)
                    val editor = sh.edit()
                    editor.putString("Description", discription)
                    editor.putString("Address",address)
                    editor.apply()
                    val intent=Intent(this,Register::class.java).putExtra("Type",selectedShopType)

                    startActivity(intent)
                    finish()
                }
                else {
                    Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

}