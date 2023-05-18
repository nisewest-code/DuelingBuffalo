package com.dueli.ngbuf.falo.utils.storege

import android.app.Application

object Bucket {
    var startUrl = ""
        set(value) {
            field = value
            PrefDuelingBuffalo.saveStartUrl(value)
        }
        get() {
            field = PrefDuelingBuffalo.getStartUrl()
            return field
        }
    var lastUrl = ""
        set(value) {
            field = value
            PrefDuelingBuffalo.saveLastUrl(value)
        }
        get() {
            field = PrefDuelingBuffalo.getLastUrl()
            return field
        }
    var status = ""
        set(value) {
            field = value
            PrefDuelingBuffalo.saveStatus(value)
        }
        get() {
            field = PrefDuelingBuffalo.getStatus()
            return field
        }
    var campaign = ""
        set(value) {
            field = value
            PrefDuelingBuffalo.saveCampaign(value)
        }
        get() {
            field = PrefDuelingBuffalo.getCampaign()
            return field
        }
    var fbclid = ""
        set(value) {
            field = value
            PrefDuelingBuffalo.saveFbclid(value)
        }
        get() {
            field = PrefDuelingBuffalo.getFbclid()
            return field
        }
    var pixelId = ""
        set(value) {
            field = value
            PrefDuelingBuffalo.savePixelId(value)
        }
        get() {
            field = PrefDuelingBuffalo.getPixelId()
            return field
        }
    var isMusic = false
        set(value) {
            field = value
            PrefDuelingBuffalo.saveMusic(value)
        }
        get() {
            field = PrefDuelingBuffalo.isMusic()
            return field
        }

    suspend fun init(context: Application) {
        PrefDuelingBuffalo.initPref(context)
    }
}