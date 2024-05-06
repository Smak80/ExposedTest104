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
                Groups,
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
            val groupId = getGroupIdByNum(groupNum)
            groupId?.let { grId ->
                try {
                    Student.new {
                        this.firstName = firstName
                        this.lastName = lastName
                        this.admYear = admYear
                        this.birth = birth
                        this.groupId = grId
                    }
                    commit()
                } catch (_:Throwable){
                    rollback()
                }
            }
        }
    }

    fun getGroupIdByNum(groupNum: String): Int?{
        return transaction(db) {
            try {
                Groups.select(Groups.id)
                    .where { Groups.groupNum eq groupNum }
                    .first()[Groups.id].value
            } catch (_: Throwable){
                null
            }
        }
    }

    fun addGroup(number: String){
        transaction {
            try {
                Group.new {
                    groupNum = number
                }
                commit()
            } catch (_: Throwable){
                rollback()
            }
        }
    }

    fun selectAllStudents(): List<StudentView>{
        return transaction {
            try {
                Join(Groups, Students, onColumn = Groups.id, otherColumn = Students.groupId)
                    .selectAll().map {
                        StudentView(
                            it[Students.id].value,
                            it[Students.lastName],
                            it[Students.firstName],
                            it[Students.admYear],
                            it[Students.birth],
                            it[Groups.groupNum]
                        )
                    }.apply{ commit() }
            } catch (_: Throwable){
                rollback()
                listOf()
            }
        }
    }

    fun renameStudent(oldLastName: String, newLastName: String){
        transaction(db) {
            try {
                Students.update(where = { Students.lastName like "$oldLastName%" }) {
                    it[lastName] = newLastName
                }
                commit()
            } catch (_:Throwable){
                rollback()
            }
        }
    }
}