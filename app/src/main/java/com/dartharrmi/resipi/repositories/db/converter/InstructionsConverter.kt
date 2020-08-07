package com.dartharrmi.resipi.repositories.db.converter

import androidx.room.TypeConverter
import com.dartharrmi.resipi.domain.InstructionStep
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class InstructionsConverter {

    @TypeConverter
    fun fromJson(value: String?): List<InstructionStep> {
        return value?.let {
            val type = object : TypeToken<List<InstructionStep>>() {}.type

            Gson().fromJson<List<InstructionStep>>(it, type)
        } ?: emptyList()
    }

    @TypeConverter
    fun toJson(ingredients: List<InstructionStep>): String = Gson().toJson(ingredients)
}
