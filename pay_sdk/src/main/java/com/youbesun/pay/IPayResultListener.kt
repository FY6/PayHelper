package com.youbesun.pay


/**
 * 支付回调接口
 *
 * @author wfy
 */
interface IPayResultListener {
    fun onPaySuccess() = Unit
    fun onPaying() = Unit
    fun onPayFail() = Unit
    fun onPayCancel() = Unit
    fun onPayConnectError() = Unit
}
