package com.example.internet_logger

import android.bluetooth.BluetoothAdapter
import android.content.Context
import android.provider.Settings
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters

class AirplaneBluetoothWorker(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {

    override fun doWork(): Result {
        val isAirplaneModeOn = Settings.Global.getInt(
            applicationContext.contentResolver,
            Settings.Global.AIRPLANE_MODE_ON, 0
        ) != 0

        val bluetoothAdapter: BluetoothAdapter? = BluetoothAdapter.getDefaultAdapter()
        val isBluetoothEnabled = bluetoothAdapter?.isEnabled == true

        Log.i("worker_airplane", "Airplane Mode: ${if (isAirplaneModeOn) "ON" else "OFF"}")
        Log.i("worker_airplane", "Bluetooth: ${if (isBluetoothEnabled) "Enabled" else "Disabled"}")

        return Result.success()
    }
}
