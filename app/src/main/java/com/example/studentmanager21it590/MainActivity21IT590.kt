package com.example.studentmanager21it590

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.studentmanager21it590.databinding.ActivityMainBinding

class MainActivity21IT590 : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnViewAll.setOnClickListener {
            val intent = Intent(this@MainActivity21IT590, ListActivity::class.java)
            startActivity(intent)
        }

        binding.btnAdd.setOnClickListener {
            val intent = Intent(this@MainActivity21IT590, AddActivity::class.java)
            startActivity(intent)
        }
    }
}