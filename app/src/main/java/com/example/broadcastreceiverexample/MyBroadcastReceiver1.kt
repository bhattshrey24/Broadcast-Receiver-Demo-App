package com.example.broadcastreceiverexample

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast

class MyBroadcastReceiver1() : BroadcastReceiver() {
    override fun onReceive(p0: Context?, p1: Intent?) {
        Toast.makeText(p0, "Triggered BroadCast receiver 1", Toast.LENGTH_SHORT).show()
        Log.i("Debugging!!", "Triggered BroadCast receiver 1")
    }
}