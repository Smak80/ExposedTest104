package ru.smak.database

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class Group(id: EntityID<Int>) : IntEntity(id) {
    var groupNum by Groups.groupNum
    companion object : IntEntityClass<Group>(Groups)
}