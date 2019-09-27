package com.example.a04_handlerscreenprocessing

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.os.SystemClock
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var isRunning:Boolean = false
    var handler:DisplayHandler? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button.setOnClickListener { view ->
            var time = System.currentTimeMillis()
            textView.text = "버튼 클릭 : ${time}"
        }

        handler = DisplayHandler()

        isRunning = true
        var thread = ThreadClass()
        thread.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        isRunning = false
    }

    // 계속 반복
    inner class ThreadClass : Thread() {
        override fun run() {
            var a1= 10
            var a2= 20

            while (isRunning) {
                SystemClock.sleep(100)
                var time = System.currentTimeMillis()
                Log.d("test1","쓰레드 : ${time}")
                // textView2.text = "쓰레드 : ${time}"
                // handler?.sendEmptyMessage(0)        // handler를 통해 화면처리
                // 메시지 객체로 핸들러에게 전달
//                var msg = Message()
//                msg.what = 0
//                msg.obj = time
//                handler?.sendMessage(msg)

                var msg2 = Message()
                msg2.what = 1
                msg2.arg1 = ++a1
                msg2.arg2 = ++a2
                msg2.obj = "Hi"
                handler?.sendMessage(msg2)
            }
        }
    }

    inner class DisplayHandler: Handler() {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)

            if(msg.what == 0) {
                var time = System.currentTimeMillis()
                textView2.text = "쓰레드 : ${msg?.obj}"
            } else if(msg?.what == 1) {
                textView2.text = "${msg?.arg1}, ${msg?.arg2}, ${msg?.obj}"
            }
        }
    }
}
