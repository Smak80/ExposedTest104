package ru.smak.database

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import java.sql.DriverManager
import java.time.LocalDate

object StudentsDatabase {

    private val db: Database

    init{
        db = Database.connect({
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
        birth: LocalDate = LocalDate.now().plusYears(-18),
        groupNum: String,
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

    fun addGroup(number: String){
        transaction {
            Group.new {
                groupNum = number
            }
            commit()
        }
    }

    fun selectAllStudents(): List<StudentView>{
        val studs = mutableListOf<StudentView>()
        transaction {
            Students.selectAll().forEachIndexed { index, it ->
                StudentView(index,
                    it[Students.lastName],
                    it[Students.firstName],
                    it[Students.admYear],
                    it[Students.birth]
                ).also { studs.add(it) }
            }
        }
        return studs
    }
}