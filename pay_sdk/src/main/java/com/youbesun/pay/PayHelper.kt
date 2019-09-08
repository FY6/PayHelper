package com.youbesun.pay

import android.app.Activity
import android.os.AsyncTask
import com.tencent.mm.opensdk.modelpay.PayReq
import com.youbesun.pay.alipay.AliPayAsyncTask
import com.youbesun.pay.wechat.WeChatHelper
import java.lang.ref.SoftReference

class PayHelper private constructor(activity: Activity) {

    private val mActivityRef: SoftReference<Activity> = SoftReference(activity)
    /*
      设置支付回调监听
   */
    private var mIPayResultListenerRef: SoftReference<IPayResultListener>? = null

    fun setPayResultCallback(listener: IPayResultListener): PayHelper {
        this.mIPayResultListenerRef = SoftReference(listener)
        return this
    }

    /*
        支付宝
        必须是异步的调用客户端支付接口
     */
    fun alPay(paySign: String) {
        val payAsyncTask = AliPayAsyncTask(mActivityRef, mIPayResultListenerRef)
        payAsyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, paySign)
    }


    /*
       微信
    */
    fun weChatPay(
        prepayId: String,
        partnerId: String,
        packageValue: String,
        timestamp: String,
        nonceStr: String,
        paySign: String
    ) {
        PayReq().let { payReq ->
            payReq.appId = WeChatHelper.APP_ID
            payReq.prepayId = prepayId
            payReq.partnerId = partnerId
            payReq.packageValue = packageValue
            payReq.timeStamp = timestamp
            payReq.nonceStr = nonceStr
            payReq.sign = paySign
            mActivityRef.get()?.let {
                WeChatHelper.build(it).callback(mIPayResultListenerRef?.get()).WXAPI.sendReq(payReq)
            }
        }
    }

    companion object {
        @JvmStatic
        fun create(activity: Activity): PayHelper = PayHelper(activity)
    }
}
