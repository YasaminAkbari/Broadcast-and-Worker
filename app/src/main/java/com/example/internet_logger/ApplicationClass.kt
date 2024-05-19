package com.example.internet_logger


import android.app.Application
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit

class ApplicationClass : Application() {

    override fun onCreate() {
        super.onCreate()

        val workRequest = PeriodicWorkRequestBuilder<AirplaneBluetoothWorker>(2, TimeUnit.MINUTES).build()
        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            "AirplaneBluetoothWorker",
            ExistingPeriodicWorkPolicy.KEEP,
            workRequest
        )
    }
}
