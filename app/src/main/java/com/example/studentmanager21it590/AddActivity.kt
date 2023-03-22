package com.example.studentmanager21it590

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.studentmanager21it590.databinding.ActivityAddBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class AddActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddBinding
    private lateinit var dbRef: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dbRef = FirebaseDatabase.getInstance().getReference("Students")

        binding.btnAdd.setOnClickListener {
            onClickAddStudent()
        }
    }
    private fun onClickAddStudent(){
        val id = binding.etId.text.toString()
        val name = binding.etName.text.toString()
        val age = binding.etAge.text.toString()
        if(id.isEmpty()){
            Toast.makeText(this, "Mã Sinh Viên không được để trống !", Toast.LENGTH_SHORT).show()
        }
        else if(name.isEmpty()){
            Toast.makeText(this, "Họ tên không được để trống !", Toast.LENGTH_SHORT).show()
        }
        else if(age.isEmpty() || age.toInt() < 18){
            Toast.makeText(this, "Tuổi không được để trống và phải không nhỏ hơn 18", Toast.LENGTH_SHORT).show()
        }
        else{
            var gender = "Nam"
            if(!binding.rbMale.isChecked){
                gender = "Nữ"
            }
            val idDb = dbRef.push().key!!
            val newStudent = StudentModel(idDb, id, name, age.toInt(), gender)

            dbRef.child(idDb).setValue(newStudent)
                .addOnCompleteListener{
                    Toast.makeText(this, "Thêm thành công", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this@AddActivity, ListActivity::class.java))
                }
                .addOnFailureListener{
                    Toast.makeText(this, "Đã có lỗi xảy ra", Toast.LENGTH_SHORT).show()
                }
        }
    }
}