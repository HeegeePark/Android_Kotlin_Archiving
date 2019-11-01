package com.example.sqlite1

import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button.setOnClickListener {
            // db 오픈
            var helper = DBHelper(this)
            var db = helper.writableDatabase

            var sql = "insert into TestTable (textData, intData, floatData, dateData) values (?, ?, ?, ?)"

            var sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            var date = sdf.format(Date())

            var arg1 = arrayOf("문자열 1", "100", "11.11", date)       // (?, ?, ?, ?) 형식에 맞추어
            var arg2 = arrayOf("문자열 2", "200", "22.22", date)       // (?, ?, ?, ?) 형식에 맞추어

            db.execSQL(sql, arg1)
            db.execSQL(sql, arg2)

            db.close()

            textView.text = "저장완료"
        }

        button2.setOnClickListener {
            var helper:DBHelper = DBHelper(this)
            var db = helper.writableDatabase

            var sql = "select * from TestTable"

            // Cursor 클래스 타입
            var c = db.rawQuery(sql, null)

            textView.text = ""

            while (c.moveToNext()){
                var idx_pos =  c.getColumnIndex("idx")
                var textData_pos = c.getColumnIndex("textData")
                var intData_pos = c.getColumnIndex("intData")
                var floatData_pos = c.getColumnIndex("floatData")
                var dateData_pos = c.getColumnIndex("dateData")

                var idx = c.getInt(idx_pos)
                var textData = c.getString(textData_pos)
                var intData = c.getInt(intData_pos)
                var floatData = c.getDouble(floatData_pos)
                var dateData = c.getString(dateData_pos)

                textView.append("idx : ${idx}\n")
                textView.append("textData : ${textData}\n")
                textView.append("intData : ${intData}\n")
                textView.append("floatData : ${floatData}\n")
                textView.append("dateData : ${dateData}\n")


                db.close()

            }

            button3.setOnClickListener {
                var helper:DBHelper = DBHelper(this)
                var db = helper.writableDatabase

                var sql = "update TestTable set textData=? where idx=?"
                var args = arrayOf("문자열3", "1")
                db.execSQL(sql, args)

                db.close()

                textView.text = "수정완료"
            }

            button4.setOnClickListener {
                var helper:DBHelper = DBHelper(this)
                var db = helper.writableDatabase

                var sql = "delete from TestTable where idx=?"
                var args = arrayOf("1")
                db.execSQL(sql, args)

                db.close()

                textView.text = "삭제완료"
            }
        }
    }
}
