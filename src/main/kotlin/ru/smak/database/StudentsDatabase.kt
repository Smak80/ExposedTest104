package ru.smak.database

import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import java.sql.DriverManager
import java.time.LocalDate

object StudentsDatabase {
    init{
        Database.connect({
            DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/db104",
                "root",
                "")
        })
        createMissingTables()
    }

    private fun createMissingTables() {
        transaction {
            SchemaUtils.createMissingTablesAndColumns(
                Students,
                inBatch = false,
                withLogs = false,
            )
        }
    }

    fun addStudent(
        firstName: String,
        lastName: String,
        admYear: Int = LocalDate.now().year,
        birth: LocalDate = LocalDate.now().plusYears(-18)
    ){
        transaction {
            Student.new {
                this.firstName = firstName
                this.lastName = lastName
                this.admYear = admYear
                this.birth = birth
            }
            commit()
        }
    }
}