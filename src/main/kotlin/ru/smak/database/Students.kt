package ru.smak.database

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.javatime.date
import java.time.LocalDate

object Students : IntIdTable("students"){
    val firstName: Column<String> = varchar(name = "first_name", length = 50)
    val lastName: Column<String> = varchar(name = "last_name", length = 50)
    val admYear: Column<Int> = integer(name = "admission").default(LocalDate.now().year)
    val birth = date("birth")
    val groupId = integer("group_id")
        .references(Groups.id,
            onDelete = ReferenceOption.RESTRICT,
            onUpdate = ReferenceOption.CASCADE)
}
