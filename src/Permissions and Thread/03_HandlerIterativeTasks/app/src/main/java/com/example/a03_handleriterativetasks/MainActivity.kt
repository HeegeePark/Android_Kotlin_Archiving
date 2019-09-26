package com.example.a03_handleriterativetasks

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.SystemClock
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    // Handler 객체를 담을 변수 초기화
    var handler : Handler? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button.setOnClickListener { view ->
            var time = System.currentTimeMillis()
            textView.text = "버튼 클릭 : ${time}"
        }

        // Handler 객체 생성
        handler = Handler()
//        // 메인 쓰레드가 한가할 틈이 없어서 화면에 어떤 것도 나타나지 않음(ANR)
//        while (true) {
//            SystemClock.sleep(100)      // 100밀리 쉼 (이것도 일하는것)
//            var time = System.currentTimeMillis()
//            textView2.text = "whiㅣe : ${time}"
//        }

        // 상속받은 쓰레드 객체로 선언 후 사용
        var thread = ThreadClass()
        handler?.post(thread)       // post 메소드가 안드로이드 OS가 한가할 때 일(thread)을 시키게 만듬.
//        // Delay를 주고싶을 때
//        handler?.postDelayed(thread, 100)

    }

    inner class ThreadClass : Thread() {
        override fun run() {
            var time = System.currentTimeMillis()
            textView2.text = "Handler : ${time}"

            // 위 일을 반복하고 싶다면 Handler에게 반복요청
            handler?.post(this)

//        // Delay를 주고싶을 때
//        handler?.postDelayed(this, 100)
        }
    }
}
