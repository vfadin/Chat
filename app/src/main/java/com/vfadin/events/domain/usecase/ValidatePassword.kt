package com.vfadin.events.domain.usecase

class ValidatePassword {
    fun execute(password: String): ValidationResult {
        if (password.length < 8) {
            return ValidationResult(
                false,
                "Пароль должен быть не менее 8 символов"
            )
        }
        if (password.length > 50) {
            return ValidationResult(
                false,
                "Пароль должен быть не более 50 символов"
            )
        }
        return ValidationResult(true, "")
    }

    fun match(password: String, passwordConfirm: String): ValidationResult {
        if (password != passwordConfirm) {
            return ValidationResult(
                false,
                "Пароли не совпадают"
            )
        }
        return ValidationResult(true, "")
    }
}