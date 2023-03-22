package com.example.studentmanager21it590

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class StudentAdapter(var listStudent:List<StudentModel>): RecyclerView.Adapter<StudentAdapter.StudentViewHolder>() {
    private lateinit var mListener: onStudentClickListener
    interface onStudentClickListener{
        fun onClickStudent(pos: Int)
    }

    fun setOnItemClickListener(clickListener: onStudentClickListener){
        mListener = clickListener
    }

    class StudentViewHolder(itemView: View, clickListener: onStudentClickListener): RecyclerView.ViewHolder(itemView){
        init {
            itemView.setOnClickListener{
                clickListener.onClickStudent(adapterPosition)
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_student, parent, false)
        return StudentViewHolder(view, mListener)
    }

    override fun getItemCount(): Int {
        return listStudent.size
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        holder.itemView.apply {
            val tvName = findViewById<TextView>(R.id.tvName)
            val tvId = findViewById<TextView>(R.id.tvId)
            tvName.text = listStudent[position].name
            tvId.text = listStudent[position].id
        }
    }
}