package com.vfadin.events.domain.usecase

class ValidateEmail {
    fun execute(email: String): ValidationResult {
        if (android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches())
            return ValidationResult(true, "")
        return ValidationResult(false, "Email введен некорректно")
    }

    fun match(email: String, emailConfirm: String): ValidationResult {
        if (email == emailConfirm)
            return ValidationResult(true, "")
        return ValidationResult(false, "Email не совпадают")
    }
}