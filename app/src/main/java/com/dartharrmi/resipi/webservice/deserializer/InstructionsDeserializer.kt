package com.dartharrmi.resipi.webservice.deserializer

import com.dartharrmi.resipi.webservice.dto.InstructionStepDTO
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

/**
 * Custom deserializer for parsing a list of steps from the recipe details response.
 *
 */
class InstructionsDeserializer: JsonDeserializer<List<InstructionStepDTO>> {

    companion object {
        /**
         * OK, this a tricky one: Kotlin compiler sees List like a covariant generic type List<out T> and compiles its instantiations as wildcard types,
         * i. e. List<InstructionStepDTO> gets compiled as List<? extends InstructionStepDTO>.
         *
         * So, in order for the deserializer to be able to parse the list, the type for the TypeToken needs to be a
         * [MutableList], which is actually invariant.
         *
         * Many thanks, variance from Java.
         */
        val typeToken: Type = object: TypeToken<MutableList<InstructionStepDTO>>() {}.type
    }

    override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): List<InstructionStepDTO> {
        return try {
            // I noticed that not all the recipes include the instructions, what's the point on that?
            if (json.asJsonArray.size() == 0) {
                emptyList()
            } else {
                json.asJsonArray.get(0).asJsonObject.getAsJsonArray("steps").map { jsonElement ->
                    with(jsonElement.asJsonObject) {
                        InstructionStepDTO(get("number").asInt, get("step").asString)
                    }
                }.toList()
            }
        } catch (e: Exception) {
            emptyList()
        }
    }
}