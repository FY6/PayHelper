package com.youbesun.pay.wechat.templates

import android.widget.Toast
import com.tencent.mm.opensdk.modelbase.BaseReq
import com.youbesun.pay.R
import com.youbesun.pay.wechat.BaseWXPayEntryActivity
import com.youbesun.pay.wechat.WeChatHelper

/**
 * 生成代码WXPayEntryActivity的父类
 *
 * @author wfy
 */
internal open class WXPayEntryTemplate : BaseWXPayEntryActivity() {

    override fun onPaySuccess() {
        WeChatHelper.build(this).callback()?.get()?.onPaySuccess()
        //不想看到微信的支付完成页面，取消动画
        finish()
        overridePendingTransition(0, 0)
    }

    override fun onPayFail() {
        WeChatHelper.build(this).callback()?.get()?.onPayFail()
        Toast.makeText(this, getString(R.string.pay_text_failed), Toast.LENGTH_SHORT).show()
        finish()
        overridePendingTransition(0, 0)
    }

    override fun onPayCancel() {
        WeChatHelper.build(this).callback()?.get()?.onPayCancel()
        finish()
        overridePendingTransition(0, 0)
    }

    override fun onReq(baseReq: BaseReq) {}
}
