package ru.chaglovne.l2.database.tables

import org.jetbrains.exposed.sql.Table

object Macros: Table() {
    val id = integer("id").autoIncrement()
    val title = varchar("title", 20)
    val isPublic = bool("isPublic")
    val description = text("description").nullable()
    val inputType = text("inputType")
    val loopType = text("loopType")
    val events = text("events")

    override val primaryKey: PrimaryKey = PrimaryKey(id, name = "PK_Macros_ID")
}