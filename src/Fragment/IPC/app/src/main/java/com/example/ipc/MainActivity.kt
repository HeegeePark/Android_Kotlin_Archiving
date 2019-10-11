package com.example.ipc

import android.app.ActivityManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var ipc_service:IPCService? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var intent = Intent(this, IPCService::class.java)
        if(isServiceRunning("com.example.ipc.IPCService") == false) {
            startService(intent)
        }
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE)      // Context.BIND_AUTO_CREATE : 현재 서비스가 동작중이 아니면 가동시키셈

        button.setOnClickListener {
            var value = ipc_service?.getNumber()
            textView.text = "value : ${value}"
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unbindService(mConnection)
    }

    // manager.getRunningServices(Int.MAX_VALUE) : 현재 실행중인 서비스 정보
    fun isServiceRunning(name:String) : Boolean{
        var manager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        for(service : ActivityManager.RunningServiceInfo in manager.getRunningServices(Int.MAX_VALUE)) {
            if(service.service.className.equals(name)) {
                return true
            }
        }
        return false
    }

    private val mConnection = object : ServiceConnection {
        override fun onServiceConnected(p0: ComponentName?, p1: IBinder?) {
            val binder = p1 as IPCService.LocalBinder
            ipc_service = binder.getService()
        }

        override fun onServiceDisconnected(p0: ComponentName?) {
            ipc_service = null
        }
    }


}
