package com.example.retailers_project

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    val firebaseAuth=FirebaseAuth.getInstance()
    private val fstore = Firebase.firestore
    lateinit var retailerList: MutableList<Retailer>
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_shops -> {
                val intent= Intent(this@MainActivity,disctict::class.java)
                startActivity(intent)
                finish()
                true
            }
            R.id.action_logout -> {
                firebaseAuth.signOut()
                val intent= Intent(this@MainActivity,Signup::class.java)
                startActivity(intent)
                finish()
                true
            }
            R.id.action_register -> {
                val intent= Intent(this@MainActivity,shopdetails::class.java)
                startActivity(intent)
                finish()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        val progressBar = findViewById<ProgressBar>(R.id.progressBar)
        progressBar.visibility=View.VISIBLE
        val recyclerView: RecyclerView = findViewById(R.id.recyclerview)
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        retailerList = mutableListOf()
        val intent=intent
        val district=intent.getStringExtra("disctrict").toString()
        val type=intent.getStringExtra("Shop_type").toString()
        val adapter=RetailersAdapter(this@MainActivity,district)
        recyclerView.adapter=adapter
        val authorizationRequestsCollection = fstore.collection(district)

        val admin = authorizationRequestsCollection // Add another condition
            .whereEqualTo("Shop_type", type)
            .get()
            .addOnSuccessListener { documents ->
                retailerList.clear()
                for (document in documents) {
                    val retailer = document.toObject(Retailer::class.java)
                    retailerList.add(retailer)
                }
                Log.d("Doc", "DocumentSnapshot added with ID:")
                // Update the RecyclerView adapter with the retrieved data
                adapter.setData(retailerList)
                progressBar.visibility=View.GONE
// Update the RecyclerView adapter with the retrieved data

            }
            .addOnFailureListener { e ->
                Log.d("Doc", "Error")  // Handle any errors
            }
    }
    }
