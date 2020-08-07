package com.dartharrmi.resipi.repositories.db.converter

import androidx.room.TypeConverter
import com.dartharrmi.resipi.domain.Ingredient
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object Converters {

    @TypeConverter
    fun fromJson(value: String?): List<Ingredient> {
        return value?.let {
            val type = object : TypeToken<List<Ingredient>>() {}.type

            Gson().fromJson<List<Ingredient>>(it, type)
        } ?: emptyList()
    }

    @TypeConverter
    fun toJson(ingredients: List<Ingredient>): String = Gson().toJson(ingredients)
}
