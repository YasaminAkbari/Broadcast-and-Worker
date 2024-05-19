package com.example.internet_logger

import android.app.Service
import android.content.Intent
import android.content.IntentFilter
import android.os.IBinder

class InternetLoggerService : Service() {
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val filter = IntentFilter()
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE")
        val receiver = InternetBroadcastReceiver()
        registerReceiver(receiver, filter)
        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}
