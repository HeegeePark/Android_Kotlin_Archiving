package com.example.lookie_ch5

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.*
import java.io.IOException

// 메인 액티비티
class MainActivity : AppCompatActivity() {
    var ID :String = et_ID.text.toString()
    var PW :String = et_Password.text.toString()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 회원가입 넘어가는 리스너
        tv_goToJoin.setOnClickListener {
            var intent = Intent(this,JoinActivity::class.java)
            startActivity(intent)
            finish()
        }

        // 로그인 시도 리스너
        bt_Login.setOnClickListener {
            // 모든 값을 입력하였을 때
            if(ID != null && PW != null) {
                var thread = LoginThread()
                thread.start()
            }else { // null이 존재 시
                Toast.makeText(this@MainActivity,"아이디 또는 비밀번호가 입력되지 않았습니다.", Toast.LENGTH_SHORT).show()
            }

        }

    }

    // 서버에 아이디와 패스워드로 로그인 요청(post)하는 스레드
    inner class LoginThread : Thread() {
        override fun run() {
            var client = OkHttpClient()

            var join_builder = Request.Builder()
            var url = join_builder.url("https://15.165.54.1/lookie/register")      // 회원가입 서버

            var multipart_builder = MultipartBody.Builder()
            multipart_builder.setType(MultipartBody.FORM)

            multipart_builder.addFormDataPart("id", ID)
            multipart_builder.addFormDataPart("pw", PW)

            var body = multipart_builder.build()


            var post = url.post(body)
            var request = post.build()
            var callback = Callback1()

            client.newCall(request).enqueue(callback)
        }
    }

    inner class Callback1 : Callback {
        // 서버와 통신 실패
        override fun onFailure(call: Call, e: IOException) {
            Log.d("실패",""+call)
            Toast.makeText(this@MainActivity,"해당 아이디 또는 패스워드가 존재하지 않습니다.", Toast.LENGTH_SHORT).show()
        }

        override fun onResponse(call: Call, response: Response) {
            println(response.body?.string())
            if(response.body?.string() == "success") {
                // 로그인드액티비티(성공화면)로 이동
                var intent = Intent(this@MainActivity,LoginedActivity::class.java)
                intent.putExtra("id",ID)
                startActivity(intent)
                Toast.makeText(this@MainActivity,"로그인에 성공하였습니다.", Toast.LENGTH_SHORT).show()
                finish()
            }
            else if(response.body?.string() == "no") {
                Toast.makeText(this@MainActivity,"해당 아이디는 존재하지 않습니다.", Toast.LENGTH_SHORT).show()
            }
            else {
                Toast.makeText(this@MainActivity,"패스워드를 다시 입력해주세요.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
