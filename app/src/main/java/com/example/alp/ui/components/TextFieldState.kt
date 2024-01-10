package com.example.alp.ui.components

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.alp.utils.ValidationRule
import com.example.alp.utils.parseValidationRules

class TextFieldState(validationRules: String) {
    private val rules: List<ValidationRule> = parseValidationRules(validationRules)
    var text: String by mutableStateOf("")
    var errors: List<String> by mutableStateOf(emptyList())
    var displayErrors: Boolean by mutableStateOf(false)
    fun serverValidation(errors: List<String>) {
        if(errors.isNotEmpty()){
            displayErrors = true
            this.errors = errors
        }
    }

    fun validate(): Boolean {
        var valid = true
        errors = emptyList()
        displayErrors = false
        for (rule in rules) {
            val (isValid, errorMessage) = rule(text)
            if (!isValid) {
                displayErrors = true
                valid = false
                errors = listOf(errorMessage)
            }
        }
        return valid
    }




}