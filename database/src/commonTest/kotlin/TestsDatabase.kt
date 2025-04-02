import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import ru.chaglovne.l2.database.DatabaseManager
import java.io.File
import kotlin.test.Test

class TestsDatabase {
    val db = File("C:\\Users\\Nikita\\IdeaProjects\\DragonSlayer\\Frontend\\frontend\\database.db")
    val databaseManager = DatabaseManager(db.absolutePath)
    @Test
    fun getMacro() = runBlocking {
        val macros = databaseManager.getAllMacros()
        val json = Json.encodeToString(macros[0])
        println(json)
    }
}