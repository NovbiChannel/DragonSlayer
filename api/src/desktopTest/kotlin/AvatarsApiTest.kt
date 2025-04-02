import com.dragon_slayer.avatars_api.AvatarsApiImpl
import kotlinx.coroutines.runBlocking
import org.junit.Test

class AvatarsApiTest {
    private val api = AvatarsApiImpl()
    @Test
    fun getPage(): Unit = runBlocking {
        val urls = api.getAvatars()
        println(urls)
    }
}