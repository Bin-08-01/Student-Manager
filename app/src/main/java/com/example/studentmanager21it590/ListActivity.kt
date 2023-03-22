package com.example.studentmanager21it590

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.studentmanager21it590.databinding.ActivityListBinding
import com.google.firebase.database.*

class ListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityListBinding
    private lateinit var dbRef: DatabaseReference
    private lateinit var listStudent: ArrayList<StudentModel>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        binding = ActivityListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvEmp.layoutManager = LinearLayoutManager(this)
        listStudent = arrayListOf<StudentModel>()
        getInfoStudents()
    }

    private fun getInfoStudents() {
        binding.rvEmp.visibility = View.GONE
        binding.txtLoadingData.visibility = View.VISIBLE

        dbRef = FirebaseDatabase.getInstance().getReference("Students")
        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                listStudent.clear()
                if (snapshot.exists()) {
                    for (each in snapshot.children) {
                        val studentInfo = each.getValue(StudentModel::class.java)
                        listStudent.add(studentInfo!!)
                    }

                    val sAdapter = StudentAdapter(listStudent)
                    val rvList = findViewById<RecyclerView>(R.id.rvEmp)
                    rvList.adapter = sAdapter

                    sAdapter.setOnItemClickListener(object : StudentAdapter.onStudentClickListener {
                        override fun onClickStudent(pos: Int) {
                            val intentd = Intent(this@ListActivity, DetailActivity::class.java)
                            intentd.putExtra("key", listStudent[pos].key)
                            intentd.putExtra("id", listStudent[pos].id)
                            intentd.putExtra("name", listStudent[pos].name)
                            intentd.putExtra("age", listStudent[pos].age.toString())
                            intentd.putExtra("gender", listStudent[pos].gender)
                            startActivity(intentd)
                        }
                    })
                    binding.rvEmp.visibility = View.VISIBLE
                    binding.txtLoadingData.visibility = View.GONE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}