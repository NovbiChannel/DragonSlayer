package ru.chaglovne.l2.database

import Macro
import inputTypeModule
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import loopTypeModule
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
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

    override fun addMacro(macro: Macro): Int {
        return transaction {
            Macros.insert {
                it[title] = macro.title
                it[isPublic] = macro.isPublic
                it[description] = macro.description
                it[inputType] = json.encodeToString(macro.inputType)
                it[loopType] = json.encodeToString(macro.loopType)
                it[events] = json.encodeToString(macro.events)
            } get(Macros.id)
        }
    }

    override fun getMacros(): List<Macro> {
        return transaction {
            Macros.selectAll().map { Macro(
                id = it[Macros.id],
                title = it[title],
                isPublic = it[isPublic],
                description = it[description],
                inputType = json.decodeFromString(it[inputType]),
                loopType = json.decodeFromString(it[loopType]),
                events = json.decodeFromString(it[events])
            ) }
        }
    }

    override fun getMacro(id: Int): Macro? {
        return try {
            transaction {
                val rr = Macros.selectAll().where { Macros.id.eq(id) }.single()
                Macro(
                    id = rr[Macros.id],
                    title = rr[title],
                    isPublic = rr[isPublic],
                    description = rr[description],
                    inputType = json.decodeFromString(rr[inputType]),
                    loopType = json.decodeFromString(rr[loopType]),
                    events = json.decodeFromString(rr[events])
                )
            }
        } catch (e: NoSuchElementException) {
            null
        }
    }
}