package com.vfadin.events.domain.usecase

class ValidateFullName {
    fun execute(fullName: String): ValidationResult {
        if (fullName.isBlank()) {
            return ValidationResult(false, "Поле не может быть пустым")
        }
        if (!fullName.matches(Regex("^[а-яА-Я]+ [а-яА-Я]+ [а-яА-Я]+$"))) {
            return ValidationResult(
                false,
                "Поле может содержать только русские буквы и пробелы"
            )
        }
        if (fullName.split(" ").size < 2) {
            return ValidationResult(
                false,
                "Поле должно содержать минимум два слова"
            )
        }
        return ValidationResult(true, "")
    }
}