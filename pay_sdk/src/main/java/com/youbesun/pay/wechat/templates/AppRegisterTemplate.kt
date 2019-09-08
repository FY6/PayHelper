package com.youbesun.pay.wechat.templates

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

/**
 * @author wfy
 */
internal open class AppRegisterTemplate : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        Log.e("tag", "微信启动广播")
    }
}