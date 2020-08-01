package com.dartharrmi.resipi.webservice.dto

import com.dartharrmi.resipi.domain.Ingredient

data class IngredientDTO(
    val id: Long?,
    val image: String?,
    val name: String?,
    val originalString: String?,
    val amount: Double?,
    val unit: String?
)

fun IngredientDTO.toDomain() = Ingredient(
    id = this.id ?: 0,
    image = this.image.orEmpty(),
    name = this.name.orEmpty(),
    originalString = this.originalString.orEmpty(),
    amount = this.amount ?: 0.0,
    unit = this.unit.orEmpty()
)