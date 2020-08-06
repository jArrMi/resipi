package com.dartharrmi.resipi.repositories.db.converter

import androidx.room.TypeConverter
import com.dartharrmi.resipi.domain.Nutrition
import com.dartharrmi.resipi.utils.fromJson
import com.google.gson.Gson

class NutritionConverter {

    @TypeConverter
    fun fromJson(value: String?): Nutrition {
        return value?.let {
            Gson().fromJson<Nutrition>(it)
        } ?: Nutrition()
    }

    @TypeConverter
    fun toJson(nutrition: Nutrition): String = Gson().toJson(nutrition)
}