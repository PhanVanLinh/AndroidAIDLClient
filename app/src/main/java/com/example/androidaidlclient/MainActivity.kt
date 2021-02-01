package com.example.androidaidlclient

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.widget.Button
import com.example.androidaidlserver.IRemoteService

class MainActivity : AppCompatActivity() {
    var iRemoteService: IRemoteService? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mConnection = object : ServiceConnection {
            override fun onServiceConnected(className: ComponentName, service: IBinder) {
                Log.i("TAG", "Service connected")
                iRemoteService = IRemoteService.Stub.asInterface(service)
            }

            override fun onServiceDisconnected(className: ComponentName) {
                Log.e("TAG", "Service has unexpectedly disconnected")
                iRemoteService = null
            }
        }

        bindService(mConnection)

        findViewById<Button>(R.id.button_get).setOnClickListener {
            Log.i("TAG", "receive : " + iRemoteService?.serverAppName)
        }

        findViewById<Button>(R.id.button_send).setOnClickListener {
            iRemoteService?.doSomeThing(1000)
        }

        findViewById<Button>(R.id.test_none_oneway_single_thread).setOnClickListener {
            for (i in 0..5) {
                Log.i("TAG", "call $i")
                iRemoteService?.noneOneWayFunctionCall(i)
            }
        }

        findViewById<Button>(R.id.test_oneway_single_thread).setOnClickListener {
for (i in 0..5) {
    Log.i("TAG", "call $i")
    iRemoteService?.onewayFunctionCall(i)
}
        }

        findViewById<Button>(R.id.test_none_oneway_multiple_thread).setOnClickListener {
            for (i in 0..5) {
                Log.i("TAG", "call $i")
                Thread {
                    iRemoteService?.noneOneWayFunctionCall(i)
                }.start()
            }
        }

        findViewById<Button>(R.id.test_oneway_multiple_thread).setOnClickListener {
            for (i in 0..5) {
                Log.i("TAG", "call $i")
                Thread {
                    iRemoteService?.onewayFunctionCall(i)
                }.start()
            }
        }
    }

    private fun bindService(mConnection: ServiceConnection) {
        val intent = Intent("RemoteService.BIND") // name of Service in Server app
        intent.setPackage("com.example.androidaidlserver") // application id of server app
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE)
    }
}