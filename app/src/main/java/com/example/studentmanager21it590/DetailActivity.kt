package com.example.studentmanager21it590

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.studentmanager21it590.databinding.ActivityDetailBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private lateinit var dbRef: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dbRef = FirebaseDatabase.getInstance().getReference("Students")

        binding.idTv.setText(intent.getStringExtra("id"))
        binding.TvdName.setText(intent.getStringExtra("name"))
        binding.tvdAge.setText(intent.getStringExtra("age"))
        if(intent.getStringExtra("gender") == "Nam"){
            binding.rbdMale.isChecked = true
        }else{
            binding.rbdFemale.isChecked = true
        }

        binding.buttonDelete.setOnClickListener {
            dbRef.child(intent.getStringExtra("key").toString()).removeValue()
                .addOnSuccessListener {
                    Toast.makeText(this, "Xoá thành công!", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this@DetailActivity, ListActivity::class.java))
                }
                .addOnFailureListener{
                    Toast.makeText(this, "Đã có lỗi xảy ra!", Toast.LENGTH_SHORT).show()
                }

        }

        binding.buttonEdit.setOnClickListener{
            val id = binding.idTv.text.toString()
            val name = binding.TvdName.text.toString()
            val age = binding.tvdAge.text.toString()
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
                if(!binding.rbdMale.isChecked){
                    gender = "Nữ"
                }
                val idDb = intent.getStringExtra("key").toString()
                val updatedData = StudentModel(idDb, id, name, age.toInt(), gender)

                dbRef.child(idDb).setValue(updatedData)
                    .addOnCompleteListener{
                        Toast.makeText(this, "Đã cập nhập thông tin của sinh viên", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this@DetailActivity, ListActivity::class.java))
                    }
                    .addOnFailureListener{
                        Toast.makeText(this, "Đã có lỗi xảy ra", Toast.LENGTH_SHORT).show()
                    }
            }
        }
    }
}