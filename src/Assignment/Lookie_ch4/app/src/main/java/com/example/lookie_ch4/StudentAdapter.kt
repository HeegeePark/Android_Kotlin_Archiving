package com.example.lookie_ch4

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import java.text.FieldPosition
import androidx.recyclerview.widget.RecyclerView

class StudentAdapter(val context: Context, val studentList : ArrayList<Student>) :
    RecyclerView.Adapter<StudentAdapter.Holder>(){
    inner class Holder(itemView:View) : RecyclerView.ViewHolder(itemView) {

        val studentName = itemView.findViewById<TextView>(R.id.tvName)
        val studentAge = itemView.findViewById<TextView>(R.id.tvAge)
        val studentMajor = itemView.findViewById<TextView>(R.id.tvMajor)

        fun bind(student : Student, context: Context) {
            studentName.text = student.name
            studentAge.text = student.age.toString()
            studentMajor.text = student.major
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType : Int): Holder {
        val view = LayoutInflater.from(context).inflate(        // LayoutInflater은 레이아웃을 쓰게해주는 아이
            R.layout.studentview,parent,false
        )
        return  Holder(view)
    }

    override fun getItemCount(): Int {
        return studentList.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(studentList[position], context)
    }


}