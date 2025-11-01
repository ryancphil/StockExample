package com.ryanphillips.rpstocks.app

import android.app.Application
import com.ryanphillips.rpstocks.core.di.CoroutineModule
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

@HiltAndroidApp // Application entry point required for HILT projects.
class RPStockApplication: Application() {

    @Inject
    @CoroutineModule.ApplicationScope
    lateinit var applicationScope: CoroutineScope
}