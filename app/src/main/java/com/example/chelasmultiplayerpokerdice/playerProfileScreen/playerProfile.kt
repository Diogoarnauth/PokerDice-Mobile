import androidx.compose.runtime.Composable
import com.example.chelasmultiplayerpokerdice.aboutScreen.AboutScreenView
import com.example.chelasmultiplayerpokerdice.aboutScreen.ProfileNavigation
import com.example.chimp.home.PlayerProfileView
import com.example.chimp.home.ProfileService

@Composable
fun playerProfile(service: ProfileService, navigator: ProfileNavigation) {
    PlayerProfileView(
        playerUsername = service.getPlayerUsername(),
        playerName = service.getPlayerName(),
        playerAge = service.getPlayerAge(),
        goBackTitleScreenFunction = {navigator.goToTitleScreen()}
    )
}