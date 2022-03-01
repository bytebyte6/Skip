package com.bytebyte6.skip

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.bytebyte6.skip.ExampleAppWidgetProvider

class ExampleAppWidgetProvider : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        Log.d(TAG, "onReceive: " + intent.action)
    }

    companion object {
        const val TAG = "ExampleAppWidgetProvider"
    }
}