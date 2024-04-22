package ru.smak

import ru.smak.database.Student
import ru.smak.database.StudentsDatabase
import java.time.LocalDate

fun main() {
    StudentsDatabase.addStudent(
        firstName = "Иван",
        lastName = "Иванов",
    )
}