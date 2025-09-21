import androidx.compose.runtime.Composable
import com.example.chelasmultiplayerpokerdice.titleScreen.TitleNavigation
import com.example.chimp.home.TitleScreenService
import com.example.chimp.home.TitleScreenView

@Composable
fun TitleScreen(service: TitleScreenService, navigator: TitleNavigation) {
    TitleScreenView(
        creators = service.getCreators(),
        startMatchFunction = { /* TODO: lógica para iniciar jogo */ },
        profileFunction = { navigator.goToPlayerProfileScreen() },
        aboutFunction = { navigator.goToAboutScreen() }
    )
}