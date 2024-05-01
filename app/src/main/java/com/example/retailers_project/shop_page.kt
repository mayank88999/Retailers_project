package com.example.retailers_project

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import com.example.retailers_project.databinding.ActivityShopPageBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class shop_page : AppCompatActivity() {
    private val fstore = Firebase.firestore
    private val firebaseAuth=FirebaseAuth.getInstance()
    private lateinit var binding: ActivityShopPageBinding
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_shops -> {
                val intent= Intent(this@shop_page,disctict::class.java)
                startActivity(intent)
                finish()
                true
            }
            R.id.action_logout -> {
                firebaseAuth.signOut()
                val intent= Intent(this@shop_page,Signup::class.java)
                startActivity(intent)
                finish()
                true
            }
            R.id.action_register -> {
                val intent= Intent(this@shop_page,shopdetails::class.java)
                startActivity(intent)
                finish()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShopPageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        val uid = intent.getStringExtra("uid")
        val dis = intent.getStringExtra("district")
        val ref = fstore.collection(dis.toString()).document(uid.toString())
        ref.get().addOnSuccessListener {
            if (it != null) {
                binding.textviewname.text = it.data?.get("Shopname") as String
                binding.textviewdes.text=it.data?.get("Description") as String
                binding.textviewemail.text=it.data?.get("Email") as String
                binding.textviewowner.text="Owner: ${it.data?.get("Name") as String}"
                binding.textviewaddress.text=it.data?.get("Address") as String
                binding.textviewphone.text="Ph ${it.data?.get("phone") as String}"
            }
        }
    }
}