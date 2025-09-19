import androidx.compose.runtime.Composable
import com.example.chelasmultiplayerpokerdice.aboutScreen.AboutNavigation
import com.example.chelasmultiplayerpokerdice.aboutScreen.AboutScreenView
import com.example.chimp.home.AboutService

@Composable
fun AboutScreen(service: AboutService, navigator: AboutNavigation) {
    AboutScreenView(
        members = service.getMembers(),
        emailList = service.getEmails(),
        gameplayUrl = service.getGamePlayUrl(),
        titleScreenFunction = {navigator.goToTitleScreen()}
    )
}