package com.example.lookie_ch3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val fruitMap = hashMapOf("사과" to R.drawable.apple, "레몬" to R.drawable.lemon, "오렌지" to R.drawable.orange)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ivImage.setImageResource(R.drawable.img_d)

        tvList.text = "검색 가능한 과일 이미지 이름 List\n ${fruitMap.keys.toString()}"


        btnSearch.setOnClickListener { view ->
            var imgname = etSearch.text.toString()
            Log.d("key","img : $imgname")

            if(imgname != null) {
                var value = fruitMap.get(imgname)!!
                ivImage.setImageResource(value)
           } else {
                Toast.makeText(this@MainActivity, "해당 이미지는 없습니다.", Toast.LENGTH_SHORT).show()

            }


        }
    }
}
