import androidx.compose.runtime.Composable
import com.example.chelasmultiplayerpokerdice.aboutScreen.AboutNavigation
import com.example.chelasmultiplayerpokerdice.titleScreen.TitleNavigation
import com.example.chimp.home.AboutService
import com.example.chimp.home.TitleScreenService
import com.example.chimp.home.TitleScreenView

@Composable
fun TitleScreen(service: TitleScreenService, navigator: TitleNavigation) {
    TitleScreenView(
       creators = service.getCreators(),
        startMatchFunction = service.getStartMatch(),
        profileFunction = service.getProfile(),
        aboutFunction = service.getAboutFunction()
    )
}