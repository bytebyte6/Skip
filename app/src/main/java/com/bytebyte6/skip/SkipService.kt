package com.bytebyte6.skip

import android.accessibilityservice.AccessibilityService
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.util.Log
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityEvent.TYPE_VIEW_CLICKED
import android.view.accessibility.AccessibilityNodeInfo
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class SkipService : AccessibilityService() {

    companion object {
        const val PING = "AccessibilityPING"
        const val PONG = "AccessibilityPONG"
        const val TAG = "SkipService"
    }

    private val pingReceiver = PingReceiver()

    private var appDataBase: AppDataBase? = null

    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    override fun onCreate() {
        super.onCreate()
        registerReceiver(pingReceiver, IntentFilter(PING))
        sendBroadcast(Intent(PONG))
        Log.d(TAG, "onCreate")
        appDataBase = AppDataBase.getAppDataBase(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(pingReceiver)
        Log.d(TAG, "onDestroy")
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        if (event != null &&
            event.eventType != TYPE_VIEW_CLICKED &&
            event.packageName != null &&
            !event.packageName.contains("setting") &&
            !event.packageName.contains("settings")
        ) {
            val list = event.source?.findAccessibilityNodeInfosByText(getString(R.string.skip))
            list?.forEach {
                if (it.isClickable) {
                    it.performAction(AccessibilityNodeInfo.ACTION_CLICK)
                    Log.d(TAG, "Click text: ${it.text}")
                } else {
                    val parent = it.parent
                    if (parent.isClickable) {
                        parent.performAction(AccessibilityNodeInfo.ACTION_CLICK)
                        Log.d(TAG, "Click parent text: ${parent.text}")
                    }
                }
                updateDb(it)
            }
        }
    }

    private fun updateDb(nodeInfo: AccessibilityNodeInfo) {
        executorService.execute {
            appDataBase?.run {
                val packageName = nodeInfo.packageName.toString()
                val entity = appDao().isExist(packageName)
                if (entity == null) {
                    val count = if (nodeInfo.isClickable || nodeInfo.parent?.isClickable == true) {
                        1
                    } else {
                        0
                    }
                    val appEntity = AppEntity(
                        packageName = packageName,
                        count = count,
                        isClickable = nodeInfo.isClickable,
                        parentIsClickable = nodeInfo.parent?.isClickable ?: false,
                        text = nodeInfo.text.toString()
                    )
                    appDao().insert(appEntity)
                } else {
                    val count = if (nodeInfo.isClickable || nodeInfo.parent?.isClickable == true) {
                        entity.count + 1
                    } else {
                        entity.count
                    }
                    val appEntity = AppEntity(
                        packageName = entity.packageName,
                        count = count,
                        isClickable = nodeInfo.isClickable,
                        parentIsClickable = nodeInfo.parent?.isClickable ?: false,
                        text = nodeInfo.text.toString()
                    )
                    appDao().update(appEntity)
                }
            }
        }
    }

    override fun onInterrupt() {
        unregisterReceiver(pingReceiver)
    }

    inner class PingReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            context?.sendBroadcast(Intent(PONG))
        }
    }
}