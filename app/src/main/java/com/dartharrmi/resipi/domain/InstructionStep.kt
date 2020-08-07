package com.dartharrmi.resipi.domain

import android.os.Parcelable
import com.dartharrmi.resipi.webservice.utils.EMPTY_STRING
import kotlinx.android.parcel.Parcelize

@Parcelize
data class InstructionStep(
        val number: Int = 0,
        val step: String = EMPTY_STRING
): Parcelable