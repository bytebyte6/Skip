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
import com.bytebyte6.skip.data.AppDataBase
import io.karn.notify.Notify
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class SkipService : AccessibilityService() {

    companion object {
        const val PING = "AccessibilityPING"
        const val PONG = "AccessibilityPONG"
        const val TAG = "SkipService"
        const val NOTIFICATION_ID = 8
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
        val notification = Notify.with(this)
            .content {
                title = getString(R.string.skip_service_enable)
                text = getString(R.string.skip_service_tips)
            }
            .asBuilder()
            .build()
        startForeground(NOTIFICATION_ID, notification)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d(TAG, "onStartCommand")
        return START_STICKY
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
                if (it.text != null && it.text.length <= 9) {
                    if (it.isClickable) {
                        it.performAction(AccessibilityNodeInfo.ACTION_CLICK)
                        Log.d(TAG, "Click: ${it.text}")
                    } else {
                        val parent = it.parent
                        if (parent != null && parent.isClickable) {
                            parent.performAction(AccessibilityNodeInfo.ACTION_CLICK)
                            Log.d(TAG, "Click parent: ${parent.text}")
                        }
                    }
                    updateDb(it, list.size)
                }
            }
        }
    }

    private fun updateDb(nodeInfo: AccessibilityNodeInfo, nodeSize: Int) {
        executorService.execute {
            appDataBase?.run {
                val packageName = nodeInfo.packageName.toString()
                val entity = logDao().get(packageName)
                if (entity == null) {
                    val count = if (nodeInfo.isClickable || nodeInfo.parent?.isClickable == true) {
                        1
                    } else {
                        0
                    }
                    logDao().insert(createLog(count, nodeSize, nodeInfo))
                } else {
                    val count = if (nodeInfo.isClickable || nodeInfo.parent?.isClickable == true) {
                        entity.count + 1
                    } else {
                        entity.count
                    }
                    logDao().update(createLog(count, nodeSize, nodeInfo))
                }
            }
        }
    }

    private fun createLog(
        count: Int,
        nodeSize: Int,
        nodeInfo: AccessibilityNodeInfo
    ): com.bytebyte6.skip.data.Log {
        var className = nodeInfo.className.toString()
        if (nodeInfo.parent != null) {
            className = className.plus(" ").plus(nodeInfo.parent.className.toString())
            if (nodeInfo.parent.parent != null) {
                className = className.plus(" ").plus(nodeInfo.parent.parent.className.toString())
            }
        }
        return com.bytebyte6.skip.data.Log(
            packageName = nodeInfo.packageName.toString(),
            count = count,
            isClickable = nodeInfo.isClickable,
            parentIsClickable = nodeInfo.parent?.isClickable ?: false,
            text = nodeInfo.text.toString(),
            nodeSize = nodeSize,
            className = className
        )
    }

    override fun onInterrupt() {
        Log.d(TAG, "onInterrupt")
        unregisterReceiver(pingReceiver)
    }

    inner class PingReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            context?.sendBroadcast(Intent(PONG))
        }
    }
}