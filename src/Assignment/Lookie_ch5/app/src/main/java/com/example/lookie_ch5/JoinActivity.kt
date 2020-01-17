package com.example.lookie_ch5

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_join.*
import okhttp3.*
import java.io.IOException

// 회원가입 액티비티
class JoinActivity : AppCompatActivity() {
    // nullabㅣe 처리
    var ID :String = et_JoinID.text.toString()
    var PW :String = et_JoinPW.text.toString()
    var PW2 :String = et_retypePW.text.toString()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_join)

        // 회원가입 시도 리스너
        bt_Join.setOnClickListener {

            // 모든 값을 입력하였을 때
            if(ID != null && PW != null && PW2 != null) {
                // pw와 재입력한 pw가 같을 시에만 서버에 회원가입 요청
                if(PW == PW2){
                    var thread = JoinThread()
                    thread.start()

                    // 메인액티비티(로그인화면)로 이동
                    var intent = Intent(this,MainActivity::class.java)
                    startActivity(intent)
                    Toast.makeText(this@JoinActivity,"회원가입에 성공하였습니다.", Toast.LENGTH_SHORT).show()
                    finish()
                }else {
                    Toast.makeText(this@JoinActivity,"패스워드와 재입력이 일치하지 않습니다.", Toast.LENGTH_SHORT).show()
                }

            }else { // null이 존재 시
                Toast.makeText(this@JoinActivity,"아이디 또는 비밀번호가 입력되지 않았습니다.", Toast.LENGTH_SHORT).show()
            }
        }

    }

    // 서버에 아이디와 패스워드로 회원가입 요청(post)하는 스레드
    inner class JoinThread : Thread() {
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
            Toast.makeText(this@JoinActivity,"이미 존재하는 아이디입니다.", Toast.LENGTH_SHORT).show()
        }

        override fun onResponse(call: Call, response: Response) {
            println(response.body?.string())
        }
    }

}
