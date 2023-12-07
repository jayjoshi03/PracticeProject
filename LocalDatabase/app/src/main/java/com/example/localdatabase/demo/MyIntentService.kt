package com.example.localdatabase.demo

import android.app.IntentService
import android.content.Intent
import android.util.Log

class MyIntentService : IntentService("MyIntentService") {
    init {
        instance = this
    }

    companion object {
        private lateinit var instance: MyIntentService
        var isRunning = false

        fun isStopService() {
            Log.d("INTENT", "Service is Stop...")
            isRunning = false
            instance.stopSelf()
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onHandleIntent(intent: Intent?) {
        try {
            isRunning = true
            while (isRunning) {
                Log.d("INTENT", "Service is Starting....")
                Thread.sleep(1000)
            }
        } catch (e: InterruptedException) {
            Thread.currentThread().interrupt()
        }
    }
}