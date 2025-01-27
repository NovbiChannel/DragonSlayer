package ru.chaglovne.l2.database

import InputType
import Macro
import inputTypeModule
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import loopTypeModule
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.statements.InsertStatement
import org.jetbrains.exposed.sql.statements.UpdateBuilder
import org.jetbrains.exposed.sql.statements.UpdateStatement
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
        transaction { SchemaUtils.create(Macros) }
    }

    override fun addMacro(macro: Macro): Int =
        transaction { Macros.insert { setMacroValues(it, macro) } get(Macros.id) }

    override fun updateMacro(macro: Macro): Boolean {
        return try {
            transaction { Macros.update { setMacroValues(it, macro) }}
            true
        } catch (e: Exception) { false }
    }

    override fun getAllMacros(): List<Macro> {
        return transaction {
            Macros.selectAll().map { rr ->
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

    override fun deleteMacro(id: Int): Boolean {
        return try {
             transaction { Macros.deleteWhere { Macros.id.eq(id) } }
            true
        } catch (e: Exception) { false }
    }

    /*
    Переписать проверку, при наличии элемента в базе и переданном идентификаторе возникает
    IllegalArgumentException: Collection has more than one element.
    */
    override fun checkAvailabilityInput(inputType: InputType, macroId: Int?): Boolean {
        return try {
            transaction {
                val rr = Macros.selectAll()
                    .where { Macros.inputType.eq(json.encodeToString(inputType)) }
                    .single()

                macroId != null && rr[Macros.id] == macroId
            }
        } catch (e: NoSuchElementException) { true }
    }

    private fun setMacroValues(builder: UpdateBuilder<Int>, macro: Macro) {
        builder[title] = macro.title
        builder[isPublic] = macro.isPublic
        builder[description] = macro.description
        builder[inputType] = json.encodeToString(macro.inputType)
        builder[loopType] = json.encodeToString(macro.loopType)
        builder[events] = json.encodeToString(macro.events)
    }
}