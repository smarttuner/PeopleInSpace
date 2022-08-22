import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.surrus.common.di.initKoin
import com.surrus.common.repository.PeopleInSpaceRepositoryInterface
import com.surrus.peopleinspace.PeopleInSpaceApp
import net.smarttuner.kaffeeverde.application.KVComposeWindow
import net.smarttuner.kaffeeverde.application.KVLifecycleWindowHolder
import net.smarttuner.kaffeeverde.lifecycle.Lifecycle

private val koin = initKoin(enableNetworkLogs = true).koin

fun main() = application {
    val windowState = rememberWindowState()

    val lifecycleHolder = remember {
        KVLifecycleWindowHolder()
    }

    val repo = koin.get<PeopleInSpaceRepositoryInterface>()

    KVComposeWindow(
        onCloseRequest = ::exitApplication,
        state = windowState,
        title = "People In Space",
        lifecycleHolder = lifecycleHolder
    ) {

        Row(Modifier.fillMaxSize()) {
            PeopleInSpaceApp(repo)
        }

    }
    LaunchedEffect(Unit){
        lifecycleHolder.lifecycle.currentState = Lifecycle.State.CREATED
        lifecycleHolder.lifecycle.currentState = Lifecycle.State.STARTED
        lifecycleHolder.lifecycle.currentState = Lifecycle.State.RESUMED
    }
}