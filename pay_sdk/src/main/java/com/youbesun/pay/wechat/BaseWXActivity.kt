package com.youbesun.pay.wechat

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler

/**
 * 如：支付，必须在包名.wxapi路径中实现WXPayEntryActivity类(包名或类名不一致会造成无法回调）
 * 登录和注册广播接收器也是需要配置，在此已经通过编译器注解生成了，而生成的类就是继承自此类
 *
 * 如：WXPayEntryTemplate继承BaseWXPayEntryActivity，而BaseWXPayEntryActivity继承BaseWXActivity，生成的类就继承WXPayEntryTemplate类
 * @author wfy
 */
internal abstract class BaseWXActivity : AppCompatActivity(), IWXAPIEventHandler {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //这个必须写在onCreate中
        WeChatHelper.build(this).WXAPI.handleIntent(intent, this)
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
        WeChatHelper.build(this).WXAPI.handleIntent(getIntent(), this)
    }
}
