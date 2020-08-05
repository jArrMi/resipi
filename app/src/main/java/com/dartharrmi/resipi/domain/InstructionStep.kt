package com.dartharrmi.resipi.domain

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class InstructionStep(
        val number: Int,
        val step: String
): Parcelable