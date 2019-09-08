# PayHelper
一行代码接入支付宝和微信支付

* 1. Add the JitPack repository to your build file

	
			allprojects {
				repositories {
						...
					maven { url 'https://jitpack.io' }
				}
			}


* 2. Add the dependency

			dependencies {
 		     kapt project(':compiler')
	             implementation 'com.github.FY6:PayHelper:version'
			}

* 3.demo


	     @PayEntryGenerator(packageName = "你的包名")
	     class MainActivity : AppCompatActivity(), IPayResultListener {
         override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_main)

        //支付宝
        PayHelper.create(this).setPayResultCallback(this).alPay("sign")

        	 //微信支付
       		 PayHelper.create(this).setPayResultCallback(this).weChatPay(
                "prepayId", "partnerId",
                "packacgeValue", "timestamp",
                "nonce", "sign"
            )
        }

        	override fun onPayCancel() {
        		Log.e("tag", "支付取消")
         	}

        	override fun onPayConnectError() {
        	Log.e("tag", "网络错误")
        }

          override fun onPayFail() {
        	Log.e("tag", "支付失败")
       	 }

        override fun onPaySuccess() {
          		Log.e("tag", "支付成功")
        }

          override fun onPaying() {
         	Log.e("tag", "正在支付")
          }
        }

具体请看demo，有什么问题请issue
