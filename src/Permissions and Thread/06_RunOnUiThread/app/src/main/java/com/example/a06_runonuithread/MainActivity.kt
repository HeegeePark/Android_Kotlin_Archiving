package com.example.a06_runonuithread

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var isRunning : Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button.setOnClickListener { view ->
            var time = System.currentTimeMillis()
            textView.text = "버튼 클릭 : ${time}"
        }

        isRunning = true
        var thread = ThreadClass()
        thread.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        isRunning = false
    }

    inner class  ThreadClass : Thread() {
        override fun run() {
            while (isRunning) {
                SystemClock.sleep(100)
                var time = System.currentTimeMillis()
                Log.d("test1", "쓰레드 : ${time}")

                // runOnUiThread는 메인 쓰레드로 가져가서 처리함.
                runOnUiThread {
                    textView2.text = "쓰레드 : ${time}"
                }
            }
        }
    }


}
