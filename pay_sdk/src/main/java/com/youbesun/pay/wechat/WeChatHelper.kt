package com.youbesun.pay.wechat

import android.app.Activity
import android.content.Context
import com.tencent.mm.opensdk.openapi.IWXAPI
import com.tencent.mm.opensdk.openapi.WXAPIFactory
import com.youbesun.pay.IPayResultListener
import java.lang.ref.SoftReference

/**
 * @author wfy
 */

internal class WeChatHelper private constructor(context: Context) {
    val WXAPI: IWXAPI

    private var mIPayResultListenerRef: SoftReference<IPayResultListener>? = null


    fun callback() = mIPayResultListenerRef

    fun callback(callback: IPayResultListener?): WeChatHelper {
        mIPayResultListenerRef?.let { if (it.get() != null) it.clear() }
        callback?.let { mIPayResultListenerRef = SoftReference(it) }
        return this
    }

    init {
        // 通过WXAPIFactory工厂，获取IWXAPI的实例
        WXAPI = WXAPIFactory.createWXAPI(context, APP_ID, true)
        // 将应用的appId注册到微信
        WXAPI.registerApp(APP_ID)
    }

    companion object {
        private var INSTANCE: WeChatHelper? = null
        /*APP_ID 替换为你的应用从官方网站申请到的合法appID*/
        const val APP_ID = ""
        const val APP_SECRET = ""

        fun build(activity: Activity): WeChatHelper {
            if (INSTANCE == null) {
                synchronized(WeChatHelper::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = WeChatHelper(activity)
                    }
                }
            }
            return INSTANCE!!
        }
    }
}
