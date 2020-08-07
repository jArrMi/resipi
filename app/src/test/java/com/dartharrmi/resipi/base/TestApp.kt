package com.dartharrmi.resipi.base

import android.app.Application
import com.squareup.picasso.Picasso
import org.mockito.Answers
import org.mockito.Mockito

class TestApp: Application() {

    private var isPicassoInitialized: Boolean = false

    override fun onCreate() {
        super.onCreate()

        if (!isPicassoInitialized) {
            isPicassoInitialized = true
            val picasso = Mockito.mock(Picasso::class.java, Answers.RETURNS_MOCKS)
            //val picasso = Picasso.Builder(this).build()
            Picasso.setSingletonInstance(picasso)
        }
    }
}