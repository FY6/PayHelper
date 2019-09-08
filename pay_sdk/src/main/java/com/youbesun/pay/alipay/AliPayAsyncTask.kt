package com.youbesun.pay.alipay

import android.app.Activity
import android.os.AsyncTask
import android.widget.Toast
import com.alipay.sdk.app.PayTask
import com.youbesun.pay.IPayResultListener
import com.youbesun.pay.R
import com.youbesun.pay.entity.AliPayResult
import java.lang.ref.SoftReference

internal class AliPayAsyncTask(
    private val mActivityRef: SoftReference<Activity>,
    private val mIAlPayResultListenerRef: SoftReference<IPayResultListener>?
) : AsyncTask<String, Void, Map<String, String>>() {

    override fun doInBackground(vararg params: String): Map<String, String> {
        val alPaySign = params[0]
        val payTask = PayTask(mActivityRef.get())
        return payTask.payV2(alPaySign, true)
    }


    override fun onPostExecute(result: Map<String, String>) {
        super.onPostExecute(result)
        val payResult = AliPayResult
            .create()
            .parseResult(result)
        when (payResult.resultStatus) {
            AL_PAY_STATUS_SUCCESS -> {
                mIAlPayResultListenerRef?.get()?.onPaySuccess()
            }
            AL_PAY_STATUS_FAIL -> {
                mIAlPayResultListenerRef?.get()?.onPayFail()
                Toast.makeText(
                    mActivityRef.get(),
                    mActivityRef.get()?.getString(R.string.pay_text_pay_failed) ?: "",
                    Toast.LENGTH_SHORT
                ).show()
            }
            AL_PAY_STATUS_PAYING -> mIAlPayResultListenerRef?.get()?.onPaying()
            AL_PAY_STATUS_CANCEL -> mIAlPayResultListenerRef?.get()?.onPayCancel()
            AL_PAY_STATUS_CONNECT_ERROR -> {//网络连接错误
                mIAlPayResultListenerRef?.get()?.onPayConnectError()
                Toast.makeText(
                    mActivityRef.get(),
                    mActivityRef.get()?.getString(R.string.pay_text_pay_failed_reson) ?: "",
                    Toast.LENGTH_SHORT
                ).show()
            }
            else -> {
            }
        }
    }

    companion object {
        //订单支付成功
        private const val AL_PAY_STATUS_SUCCESS = "9000"
        //订单处理中
        private const val AL_PAY_STATUS_PAYING = "8000"
        //订单支付失败
        private const val AL_PAY_STATUS_FAIL = "4000"
        //用户取消
        private const val AL_PAY_STATUS_CANCEL = "6001"
        //支付网络错误
        private const val AL_PAY_STATUS_CONNECT_ERROR = "6002"
    }
}
