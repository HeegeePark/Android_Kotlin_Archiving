package com.example.a02_thread

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var isRunning = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button.setOnClickListener {view ->
            var now = System.currentTimeMillis()
            textView.text= "버튼 클릭 : ${now}"
        }

//        // 이 부분 처리하느라 너무 바빠서 실행하면 화면에 아무것도 안뜨게 됨 = ANR. (이럴때는 쓰레드 사용할것!!)
//        while (true) {
//            var now = System.currentTimeMillis()
//            textView2.text = "무한 루프 : ${now}"
//        }

        isRunning = true
        var thread = ThreadClass1()
        thread.start()
    }

    // 개발자가 만든 쓰레드
    inner class ThreadClass1 : Thread() {
        override fun run() {
            while (isRunning) {
                SystemClock.sleep(100)
                var now = System.currentTimeMillis()
                Log.d("test1", "쓰레드 : ${now}")

                // 화면에 처리하기 (오레오 버전 이상만 가능)
                textView2.text = "쓰레드 : ${now}"
            }
        }
    }
    // 앱을 종료하면 메인쓰레드는 종료되어도 내가 만든 쓰레드는 종료되지 않는다.
    // 같이 끄고 싶으면 와일문 false로 바꾸고 조건걸기!

    override fun onDestroy() {
        super.onDestroy()
        isRunning =false
    }
}
