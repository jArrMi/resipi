package com.dartharrmi.resipi.webservice.dto

import com.dartharrmi.resipi.domain.InstructionStep

data class InstructionStepDTO(val number: Int?,
                              val step: String?,
                              val ingredients: List<Long>?)

fun InstructionStepDTO.toDomain() = InstructionStep(
        number = this.number ?: 0,
        step = this.step.orEmpty(),
        ingredients = this.ingredients?.map { it } ?: emptyList()
)