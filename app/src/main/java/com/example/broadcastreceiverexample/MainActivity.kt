package com.example.broadcastreceiverexample

import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.broadcastreceiverexample.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater, null, false)
    }
    private lateinit var myBroadCastReceiver: MyBroadcastReceiver1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        if (!hasReadPermission() || hasReceivePermission()) {
            requestPermission()
        }
        myBroadCastReceiver = MyBroadcastReceiver1()

        binding.customBtn.setOnClickListener { // sending
            // custom intent to a broadcast receiver
            val intent = Intent(this, MyBroadcastReceiver2::class.java)
            intent.setAction("my.custom.intent")
            sendBroadcast(intent)
        }
    }

    override fun onStart() { // Starting in onStart and stopping in onStop so that
        // user don't drain battery by listeninng to the event unnecessary
        super.onStart()
        val intentFilter = IntentFilter("android.provider.Telephony.SMS_RECEIVED")
        registerReceiver(myBroadCastReceiver, intentFilter)
    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver(myBroadCastReceiver)
    }

    private fun requestPermission() {
        val permissionsToRequest = mutableListOf<String>()

        if (!hasReadPermission()) {
            permissionsToRequest.add(
                android.Manifest.permission.READ_SMS
            )
        }

        if (!hasReceivePermission()) {
            permissionsToRequest.add(
                android.Manifest.permission.RECEIVE_SMS
            )
        }


        if (permissionsToRequest.isNotEmpty()) { // ie. if there are some permissions that
            // user has not accepted so we will now ask user to accept them
            ActivityCompat.requestPermissions(
                this, permissionsToRequest.toTypedArray(), 101
            )
        }

    }

    private fun hasReadPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            this, android.Manifest.permission.READ_SMS
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun hasReceivePermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            this, android.Manifest.permission.RECEIVE_SMS
        ) == PackageManager.PERMISSION_GRANTED
    }
}