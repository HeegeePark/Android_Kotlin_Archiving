package com.example.a05_asyntask

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button.setOnClickListener { view ->
            var time = System.currentTimeMillis()
            textView.text = "버튼 클릭 : ${time}"
        }

        var sync = AsynTaskClass()
        sync.execute(10,20)
    }

    inner class AsynTaskClass : AsyncTask<Int, Long, String> {

        override fun onPreExecute() {
            super.onPreExecute()
            textView2.text = "AsyncTask 가동"
        }

        override fun doInBackground(vararg p0: Int?): String {      // p0에 배열의 형태로 파라미터 값들 받음
            var a1 = p0[0]!!        // !!는 널이 허용 가능한 변수에 허용하지 않을 때 표기해줌.
            var a2 = p0[1]!!

            for (idx in 0..9) {     // 0부터 9까지 돌기
                SystemClock.sleep(100)

                a1++
                a2++

                Log.d("test1","${idx} : ${a1}, ${a2}")
                // 일반 쓰레드에서 화면 관련 작업 시 오류 발생
                // textView2.text = "${idx} : ${a1}, ${a2}"

                // publishProgress는 메인쓰레드에서 처리하므로 화면 처리 가능
                var time = System.currentTimeMillis()
                publishProgress(time)
            }

            return "수행이 완료되었습니다."
        }

        override fun onProgressUpdate(vararg values: Long?) {
            super.onProgressUpdate(*values)

            textView2.text = "Async : ${values[0]}"
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            textView2.text = result
        }
    }
}


