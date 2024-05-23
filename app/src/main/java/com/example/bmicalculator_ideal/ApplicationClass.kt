package com.example.bmicalculator_ideal

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import android.os.Handler
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent

class ApplicationClass : Application(), LifecycleObserver, Application.ActivityLifecycleCallbacks {
    override fun onCreate() {
        super.onCreate()
        context = this
        registerActivityLifecycleCallbacks(this)
        applicationHandler = Handler(
            applicationContext.mainLooper
        )
    }

    companion object {
        @Volatile
        lateinit var applicationHandler: Handler
        fun getContext(): Context {
            return context.applicationContext
        }

        private lateinit var context: Context
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onMoveToForeground() {
//        if(isShowOpen)
//            admobOpen.showOpenAd(context as Activity) {}
    }

    override fun onActivityCreated(p0: Activity, p1: Bundle?) {
    }

    override fun onActivityStarted(p0: Activity) {
        context = p0
    }

    override fun onActivityResumed(p0: Activity) {

    }

    override fun onActivityPaused(p0: Activity) {
    }

    override fun onActivityStopped(p0: Activity) {
    }

    override fun onActivitySaveInstanceState(p0: Activity, p1: Bundle) {
    }

    override fun onActivityDestroyed(p0: Activity) {
    }
}