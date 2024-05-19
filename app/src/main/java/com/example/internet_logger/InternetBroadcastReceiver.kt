package com.example.internet_logger

import android.Manifest
import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import org.json.JSONObject
import java.io.File
import java.io.FileWriter

class InternetBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (context == null) return
        val isConnected = isNetworkAvailable(context)
        val status = if (isConnected) "Connected" else "Disconnected"


        showNotification(context, status)

        val updateUI = Intent("UPDATE_UI")
        updateUI.putExtra("status", status)
        LocalBroadcastManager.getInstance(context).sendBroadcast(updateUI)

        logToFile(context, "internet", status)
    }

    private fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo: NetworkInfo? = connectivityManager.activeNetworkInfo
        return activeNetworkInfo?.isConnected == true
    }

    private fun showNotification(context: Context, status: String) {
        val notificationManager = NotificationManagerCompat.from(context)
        val builder = NotificationCompat.Builder(context, "internet_status")
            .setSmallIcon(R.drawable.baseline_circle_notifications_24)
            .setContentTitle("Internet Status")
            .setContentText("Internet is $status")
            .setPriority(NotificationCompat.PRIORITY_HIGH)

        if (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            ActivityCompat.requestPermissions(context as Activity, arrayOf(Manifest.permission.POST_NOTIFICATIONS), 1)
        } else {

            notificationManager.notify(1, builder.build())
        }
    }

    private fun logToFile(context: Context, type: String, status: String) {
        try {
            val log = JSONObject().apply {
                put("timestamp", System.currentTimeMillis())
                put("type", type)
                put("status", status)
            }

            val file = File(context.filesDir, "log.json")
            FileWriter(file, true).use {
                it.write("$log\n")
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
