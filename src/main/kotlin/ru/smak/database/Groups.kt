package ru.smak.database

import org.jetbrains.exposed.dao.id.IntIdTable

object Groups : IntIdTable("groups") {
    val groupNum = varchar("group_num", 7)
        .index(isUnique = true)
}