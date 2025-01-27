import events.DatabaseEvents
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

object EventManager {
    private val _dbEventsFlow = MutableSharedFlow<DatabaseEvents>()
    private val _msgEventsFlow = MutableSharedFlow<String>()
    val dbEventsFlow: Flow<DatabaseEvents> = _dbEventsFlow.asSharedFlow()
    val msgEventsFlow: Flow<String> = _msgEventsFlow

    suspend fun newRecordEvent(recordId: Int) = _dbEventsFlow.emit(DatabaseEvents.NewRecord(recordId))
    suspend fun updateEvent(recordId: Int) = _dbEventsFlow.emit(DatabaseEvents.Update(recordId))
    suspend fun sendMessage(message: String) = _msgEventsFlow.emit(message)
}