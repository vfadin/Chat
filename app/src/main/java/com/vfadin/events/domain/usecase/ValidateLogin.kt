package com.vfadin.events.domain.usecase

class ValidateLogin {
    fun execute(login: String): ValidationResult {
        if (login.length < 3) {
            return ValidationResult(
                false,
                "Логин должен быть не менее 3 символов"
            )
        }
        if (login.length > 50) {
            return ValidationResult(
                false,
                "Логин должен быть не более 20 символов"
            )
        }
        if (!login.matches(Regex("[a-zA-Z0-9-]+"))) {
            return ValidationResult(
                false,
                "Логин может содержать только латинские буквы, цифры и дефис"
            )
        }
        return ValidationResult(true, "")
    }
}