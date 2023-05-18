package com.dueli.ngbuf.falo.router

import android.content.Context
import android.content.Intent

object Router {
    fun<T> routeOnPage(context: Context, cl: Class<T>){
        context.startActivity(Intent(context, cl))
    }
}