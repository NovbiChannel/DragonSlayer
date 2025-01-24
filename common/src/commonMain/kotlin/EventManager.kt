import events.DatabaseEvents
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

object EventManager {
    private val _eventsFlow = MutableSharedFlow<DatabaseEvents>()
    val eventsFlow: Flow<DatabaseEvents> = _eventsFlow.asSharedFlow()

    suspend fun newRecordEvent(recordId: Int) = _eventsFlow.emit(DatabaseEvents.NewRecord(recordId))
}