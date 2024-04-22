package ru.smak.database

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class Student(id: EntityID<Int>) : IntEntity(id) {
    var firstName by Students.firstName
    var lastName by Students.lastName
    var admYear by Students.admYear
    var birth by Students.birth

    companion object : IntEntityClass<Student>(Students)
}