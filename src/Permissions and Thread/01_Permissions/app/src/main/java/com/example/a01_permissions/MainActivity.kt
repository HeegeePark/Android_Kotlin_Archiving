package com.example.a01_permissions

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var permission_list =arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.READ_CONTACTS,
        Manifest.permission.WRITE_CONTACTS,
        Manifest.permission.SEND_SMS,
        Manifest.permission.RECEIVE_SMS
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        checkPermission()
    }

    fun checkPermission() {
        // 마쉬멜로우 이하 버전은 권한 사용 고지를 하지 않음.
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return;
        }

        for (permission : String in permission_list) {
            var chk = checkCallingOrSelfPermission(permission)

            //  권한이 비활성화일 때
            if(chk== PackageManager.PERMISSION_DENIED) {
                requestPermissions(permission_list,0);
                break;
            }
        }
    }

    // 권한 승인 질문 다이어로그가 사라진 후 작동
    // permissions: Array<out String> : 체크한 권한들의 배열
    // grantResults: IntArray : 해당 권한이 허용인지 아닌지 그 값이 담겨있는 배열
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        var idx = 0;

        textView.text=""
        for (idx in grantResults.indices) {
            if(grantResults[idx] == PackageManager.PERMISSION_GRANTED) {
                textView.append("${permission_list[idx]} : 허용함\n");
            }
            else {
                textView.append("${permission_list[idx]} : 허용하지 않음\n");
            }
        }
    }
}
