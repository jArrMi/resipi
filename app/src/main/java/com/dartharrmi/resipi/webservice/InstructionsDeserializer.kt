package com.dartharrmi.resipi.webservice

import com.dartharrmi.resipi.webservice.dto.InstructionStepDTO
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class InstructionsDeserializer: JsonDeserializer<List<InstructionStepDTO>> {

    companion object {
        val typeToken: Type = object: TypeToken<MutableList<InstructionStepDTO>>() {}.type
    }

    override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): List<InstructionStepDTO> {
        val jsonArray = json.asJsonArray

        return (jsonArray[0] as JsonObject).getAsJsonArray("steps").map { currentStep ->
            with(currentStep.asJsonObject) {
                val ingredients = this.getAsJsonArray("ingredients").map {
                    it.asJsonObject.get("id").asLong
                }

                return@map InstructionStepDTO(
                        number = get("number").asInt,
                        step = get("step").asString,
                        ingredients = ingredients
                )
            }
        }.toList()
    }
}