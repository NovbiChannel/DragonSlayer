package events

sealed class DatabaseEvents {
    data class NewRecord(val macroId: Int): DatabaseEvents()
    data class Update(val macroId: Int): DatabaseEvents()
    data class Delete(val macroId: Int): DatabaseEvents()
}