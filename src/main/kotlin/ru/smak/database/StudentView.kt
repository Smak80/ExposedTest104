package ru.smak.database

import java.time.LocalDate

data class StudentView(
    var id: Int,
    var firstName: String,
    var lastName: String,
    var admYear: Int,
    var birth: LocalDate,
    var groupNum: String,
)