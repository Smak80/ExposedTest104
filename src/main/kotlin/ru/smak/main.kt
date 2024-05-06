package ru.smak

import ru.smak.database.StudentsDatabase

fun main() {
    StudentsDatabase.addGroup("05-104")
    StudentsDatabase.addGroup("05-105")
    StudentsDatabase.addStudent(
        firstName = "Петр",
        lastName = "Петров",
        groupNum = "05-104"
    )
    StudentsDatabase.addStudent(
        firstName = "Александр",
        lastName = "Сидоров",
        groupNum = "05-105"
    )
    StudentsDatabase.renameStudent("Петр", "Васечкин")
    StudentsDatabase.selectAllStudents().forEach {
        println(it)
    }
}