package com.dueli.ngbuf.falo

import android.app.Application
import com.dueli.ngbuf.falo.analytics.Analytics
import com.dueli.ngbuf.falo.utils.DeviceUtil
import com.dueli.ngbuf.falo.utils.lib.OneLib
import com.dueli.ngbuf.falo.utils.storege.Bucket
import com.dueli.ngbuf.falo.utils.str.Ids
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AppDuelingBuffalo: Application() {

    override fun onCreate() {
        super.onCreate()
        CoroutineScope(Dispatchers.Default).launch {
            Bucket.init(this@AppDuelingBuffalo)
        }
        Analytics.init(this)

//        Bugsee.launch(this, Ids.bugId())

        DeviceUtil.checkDevice(
            this, callbackFail = {},
            callbackSuccess = {
                OneLib.init(this)
            }
        )

        // promptForPushNotifications will show the native Android notification permission prompt.
        // We recommend removing the following code and instead using an In-App Message to prompt for notification permission (See step 7)
    }
}