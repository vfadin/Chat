package com.vfadin.events.domain.usecase

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Calendar

class ValidateDate {
    fun execute(date: String): ValidationResult {
        if (date.isEmpty()) {
            return ValidationResult(false, "Введите дату рождения")
        }
        val dateSplit = date.split(".")
        if (dateSplit.size != 3) {
            return ValidationResult(false, "Дата должна быть в формате ДД.ММ.ГГГГ")
        }
        if (dateSplit[0].length != 2 || dateSplit[1].length != 2 || dateSplit[2].length != 4) {
            return ValidationResult(false, "Дата должна быть в формате ДД.ММ.ГГГГ")
        }
        val day = dateSplit[0].toInt()
        val month = dateSplit[1].toInt()
        val year = dateSplit[2].toInt()
        if (day !in 1..31 || month !in 1..12 || year !in 1900..2100) {
            return ValidationResult(false, "Дата должна быть в формате ДД.ММ.ГГГГ")
        }
        if (month == 2 && day > 29) {
            return ValidationResult(false, "Февраль не может иметь больше 29 дней")
        }
        if (month == 2 && day == 29 && !isLeapYear(year)) {
            return ValidationResult(false, "В $year году февраль имел 28 дней")
        }
        if (checkDateMoreThanFiveYearsAgo(date)) {
            ValidationResult(false, "Возраст не может быть меньше 5 лет")
        }
        return ValidationResult(true, "")
    }

    private fun isLeapYear(year: Int): Boolean {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.YEAR, year)
        return calendar.getActualMaximum(Calendar.DAY_OF_YEAR) > 365
    }
    
    private fun checkDateMoreThanFiveYearsAgo(date: String): Boolean {
        val dateSplit = date.split(".")
        val day = dateSplit[0].toInt()
        val month = dateSplit[1].toInt()
        val year = dateSplit[2].toInt()
        val currentDateFormatted =
            LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
        val currentDateSplit = currentDateFormatted.split(".")
        val currentDay = currentDateSplit[0].toInt()
        val currentMonth = currentDateSplit[1].toInt()
        val currentYear = currentDateSplit[2].toInt()
        if (currentYear - year < 5) {
            return false
        }
        if (currentYear - year == 5) {
            if (currentMonth > month) {
                return false
            }
            if (currentMonth == month && currentDay >= day) {
                return false
            }
        }
        return true
    }
}