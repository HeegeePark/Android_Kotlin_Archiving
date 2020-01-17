package com.example.lookie_ch5

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_logined.*

// 로그인 성공 시 들어오는 액티비티
class LoginedActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_logined)

        var user = intent.getStringExtra("id")
        tv_string.text= user + "님께서 로그인하셨습니다:)"

        // 로그아웃 시도 리스너
        bt_Logout.setOnClickListener {
            var intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }
    }
}
