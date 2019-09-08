package com.wfy.pay

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity


//@AppRegisterGenerator(packageName = "com.pay")
//@PayEntryGenerator(packageName = "com.pay")
class MainActivity : AppCompatActivity()//, IPayResultListener
{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        //支付宝
//        PayHelper.create(this).setPayResultCallback(this).alPay("sign")
//
//        //微信支付
//        PayHelper.create(this).setPayResultCallback(this)
//            .weChatPay(
//                "prepayId", "partnerId",
//                "packacgeValue", "timestamp",
//                "nonce", "sign"
//            )
    }
//
//    override fun onPayCancel() {
//        Log.e("tag", "支付取消")
//    }
//
//    override fun onPayConnectError() {
//        Log.e("tag", "网络错误")
//    }
//
//    override fun onPayFail() {
//        Log.e("tag", "支付失败")
//    }
//
//    override fun onPaySuccess() {
//        Log.e("tag", "支付成功")
//    }
//
//    override fun onPaying() {
//        Log.e("tag", "正在支付")
//    }
}
