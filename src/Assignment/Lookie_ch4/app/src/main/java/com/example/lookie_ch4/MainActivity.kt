package com.example.lookie_ch4

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var studentList = arrayListOf<Student>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btInput.setOnClickListener {
            var builder = AlertDialog.Builder(this)
            val dialogView = layoutInflater.inflate(R.layout.dialogview, null)

            val dialogName = dialogView.findViewById<EditText>(R.id.etName)
            val dialogAge = dialogView.findViewById<EditText>(R.id.etAge)
            val dialogMajor = dialogView.findViewById<EditText>(R.id.etMajor)

            // builder를 통한 확인 버튼과 취소 버튼
            builder.setView(dialogView)
                .setPositiveButton("확인") { dialogInterface, i ->
                    val student = Student(dialogName.text.toString(), dialogAge.text as Int, dialogMajor.text.toString())
                    studentList.add(student)
                    /* 확인일 때 main의 View의 값에 dialog View에 있는 값을 적용 */

                }
                .setNegativeButton("취소") { dialogInterface, i ->
                    /* 취소일 때 아무 액션이 없으므로 빈칸 */
                }
                .show()

        }

        val mAdapter = StudentAdapter(this, studentList)
        recyclerView.adapter = mAdapter

        val lm = LinearLayoutManager(this)
        recyclerView.layoutManager = lm
        lm.reverseLayout = true     // 항상 리스트의 가장 위에 추가
        recyclerView.setHasFixedSize(true)      //바뀔 수 있는 크기를 고정
    }
}
