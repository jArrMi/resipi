package com.dartharrmi.resipi.domain.exceptions

class RecipesNotfoundException(private val recipeQuery: String) :
    Exception("Query {$recipeQuery} did not produce any output")