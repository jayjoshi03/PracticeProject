package com.example.localdatabase.demo

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log


class MyService : Service() {
    init {
        Log.d("SERVICE", "Service Running...")
    }

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        var dataParse = intent?.getStringExtra("EXTRA")
        dataParse?.let {
            Log.d("SERVICE",dataParse)
        }
        return START_STICKY
    }
}