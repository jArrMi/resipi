package com.dartharrmi.resipi.webservice.dto

import android.os.Parcelable
import com.dartharrmi.resipi.domain.InstructionStep
import kotlinx.android.parcel.Parcelize

@Parcelize
data class InstructionStepDTO(
        val number: Int?,
        val step: String?
): Parcelable

fun InstructionStepDTO.toDomain() = InstructionStep(
        number = this.number ?: 0,
        step = this.step.orEmpty()
)