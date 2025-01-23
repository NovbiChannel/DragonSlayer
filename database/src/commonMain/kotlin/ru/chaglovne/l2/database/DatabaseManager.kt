package ru.chaglovne.l2.database

import Macro
import inputTypeModule
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import loopTypeModule
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import ru.chaglovne.l2.database.dao.MacrosDAO
import ru.chaglovne.l2.database.tables.Macros
import ru.chaglovne.l2.database.tables.Macros.description
import ru.chaglovne.l2.database.tables.Macros.events
import ru.chaglovne.l2.database.tables.Macros.inputType
import ru.chaglovne.l2.database.tables.Macros.isPublic
import ru.chaglovne.l2.database.tables.Macros.loopType
import ru.chaglovne.l2.database.tables.Macros.title
import timeUnitModule

class DatabaseManager(private val dbFilePath: String): MacrosDAO {

    private val json = Json {
        inputTypeModule
        loopTypeModule
        timeUnitModule
    }

    init {
        connect()
        createTable()
    }

    private fun connect() {
        Database.connect("jdbc:sqlite:$dbFilePath", driver = "org.sqlite.JDBC")
    }

    private fun createTable() {
        transaction {
            SchemaUtils.create(Macros)
        }
    }

    override fun addMacro(macro: Macro) {
        transaction {
            Macros.insert {
                it[title] = macro.title
                it[isPublic] = macro.isPublic
                it[description] = macro.description
                it[inputType] = json.encodeToString(macro.inputType)
                it[loopType] = json.encodeToString(macro.loopType)
                it[events] = json.encodeToString(macro.events)
            }
        }
    }

    override fun getMacros(): List<Macro> {
        return transaction {
            Macros.selectAll().map { Macro(
                title = it[title],
                isPublic = it[isPublic],
                description = it[description],
                inputType = json.decodeFromString(it[inputType]),
                loopType = json.decodeFromString(it[loopType]),
                events = json.decodeFromString(it[events])
            ) }
        }
    }
}