package com.dartharrmi.resipi.base

import android.app.Application
import com.dartharrmi.resipi.utils.ResipiLog
import com.squareup.picasso.Picasso
import org.mockito.Answers
import org.mockito.Mockito

class TestApp: Application() {

    companion object {
        val tag = ResipiLog.makeLogTag(TestApp::class.java)
    }

    private var isPicassoInitialized: Boolean = false

    override fun onCreate() {
        super.onCreate()

        if (!isPicassoInitialized) {
            val picasso = Mockito.mock(Picasso::class.java, Answers.RETURNS_MOCKS)
            try {
                Picasso.setSingletonInstance(picasso)
                isPicassoInitialized = true
            } catch (e: IllegalStateException) {
                ResipiLog.LOGE("TestApp", "Picasso already exists, skipping", e)
            }
        }
    }
}