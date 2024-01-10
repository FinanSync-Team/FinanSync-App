package com.example.alp.utils


typealias ValidationRule = (String) -> Pair<Boolean, String>

val requiredRule: ValidationRule = { input ->
    input.isNotEmpty() to "Field is required"
}

val emailRule: ValidationRule = { input ->
    input.matches(Regex("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")) to "Invalid email"
}

val numericRule: ValidationRule = { input ->
    input.matches(Regex("[0-9]+")) to "Invalid number"
}

fun inRule(vararg validInputs: String): ValidationRule = { input ->
    (validInputs.contains(input)) to "Invalid input"
}

fun minRule(minLength: Int): ValidationRule = { input ->
    (input.length >= minLength) to "Minimum length of $minLength characters required"
}

fun maxRule(maxLength: Int): ValidationRule = { input ->
    (input.length <= maxLength ) to "Maximum length of $maxLength characters allowed"
}

fun parseValidationRules(ruleString: String): List<ValidationRule> {
    return ruleString.split("|").mapNotNull { ruleName ->
        when {
            ruleName.startsWith("numeric") -> numericRule
            ruleName.startsWith("required") -> requiredRule
            ruleName.startsWith("email") -> emailRule
            ruleName.startsWith("min:") -> {
                val minLength = ruleName.substringAfter("min:").toIntOrNull() ?: 0
                minRule(minLength)
            }
            ruleName.startsWith("max:") -> {
                val maxLength = ruleName.substringAfter("max:").toIntOrNull() ?: Int.MAX_VALUE
                maxRule(maxLength)
            }
            ruleName.startsWith("in:") -> {
                val validInputs = ruleName.substringAfter("in:").split(",")
                inRule(*validInputs.toTypedArray())
            }
            // Add more cases for other rules
            else -> null
        }
    }
}