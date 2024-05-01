package com.example.retailers_project

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ProgressBar
import androidx.appcompat.widget.Toolbar
import com.example.retailers_project.databinding.ActivityDisctictBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class disctict : AppCompatActivity() {
    lateinit var binding: ActivityDisctictBinding
    val firebaseAuth = FirebaseAuth.getInstance()
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_shops -> {
                val intent=Intent(this@disctict,disctict::class.java)
                startActivity(intent)
                finish()
                true
            }
            R.id.action_logout -> {
                firebaseAuth.signOut()
                val intent=Intent(this@disctict,Signup::class.java)
                startActivity(intent)
                finish()
                true
            }
            R.id.action_register -> {
                val intent=Intent(this@disctict,shopdetails::class.java)
                startActivity(intent)
                finish()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityDisctictBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        val spinner=binding.spinner3
        val districts = resources.getStringArray(R.array.districts_array)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item,districts)
        spinner.adapter = adapter
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedDistrict = districts[position]// Handle item selection if needed
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Handle case where nothing is selected
            }
        }
        binding.layoutdoc.setOnClickListener{
            binding.progressBar.visibility=View.VISIBLE
            val selectedDistrict = binding.spinner3.selectedItem.toString()
            intent=Intent(this,MainActivity::class.java).putExtra("disctrict",selectedDistrict).putExtra("Shop_type","Doctor")
            startActivity(intent)
            finish()
            binding.progressBar.visibility=View.GONE
        }
        binding.layoutmedical.setOnClickListener{
            binding.progressBar.visibility=View.VISIBLE
            val selectedDistrict = binding.spinner3.selectedItem.toString()
            intent=Intent(this,MainActivity::class.java).putExtra("disctrict",selectedDistrict).putExtra("Shop_type","Vegetable Shop")
            startActivity(intent)
            finish()
            binding.progressBar.visibility=View.GONE
        }
        binding.layoutstore.setOnClickListener{
            binding.progressBar.visibility=View.VISIBLE
            val selectedDistrict = binding.spinner3.selectedItem.toString()
            intent=Intent(this,MainActivity::class.java).putExtra("disctrict",selectedDistrict).putExtra("Shop_type","General Store")
            startActivity(intent)
            finish()
            binding.progressBar.visibility=View.GONE
        }
        binding.layoutmedical.setOnClickListener {
            binding.progressBar.visibility=View.VISIBLE
            val selectedDistrict = binding.spinner3.selectedItem.toString()
            intent=Intent(this,MainActivity::class.java).putExtra("disctrict",selectedDistrict).putExtra("Shop_type","Medical Store")
            startActivity(intent)
            finish()
            binding.progressBar.visibility=View.GONE
        }
        binding.btnother.setOnClickListener {
            binding.progressBar.visibility=View.VISIBLE
            val selectedDistrict = binding.spinner3.selectedItem.toString()
            intent=Intent(this,MainActivity::class.java).putExtra("disctrict",selectedDistrict).putExtra("Shop_type","Others")
            startActivity(intent)
            finish()
            binding.progressBar.visibility=View.GONE
        }
    }
}