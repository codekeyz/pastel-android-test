package com.codekeyz.newsfeed

import android.app.Application
import com.codekeyz.newsfeed.data.AppContainer
import com.codekeyz.newsfeed.data.AppContainerImpl

class NewsfeedApp: Application() {

    // AppContainer instance used by the rest of classes to obtain dependencies
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppContainerImpl(this)
    }
}