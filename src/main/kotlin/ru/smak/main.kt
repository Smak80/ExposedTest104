package ru.smak

import ru.smak.database.StudentsDatabase

fun main() {
    StudentsDatabase.addGroup("05-104")
    StudentsDatabase.addStudent(
        firstName = "Иван",
        lastName = "Иванов",
    )
    StudentsDatabase.selectAllStudents().forEach {
        println(it)
    }
}