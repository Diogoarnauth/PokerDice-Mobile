package com.example.chelasmultiplayerpokerdice

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.chelasmultiplayerpokerdice.domain.AuthenticatedUser
import com.example.chelasmultiplayerpokerdice.playerProfile.PlayerProfileNavigation
import com.example.chelasmultiplayerpokerdice.playerProfile.PlayerProfileScreen
import com.example.chelasmultiplayerpokerdice.playerProfile.PlayerProfileViewModel
import com.example.chelasmultiplayerpokerdice.playerProfile.PlayerProfileViewModelFactory

class PlayerProfileActivity : ComponentActivity() {

    private val app by lazy { application as DependenciesContainer }

    private val playerProfileNavigation: PlayerProfileNavigation by lazy { NavigationIntentImpl(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        @Suppress("DEPRECATION")
        val user = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
            intent.getSerializableExtra(AUTHENTICATED_USER_EXTRA, AuthenticatedUser::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getSerializableExtra(AUTHENTICATED_USER_EXTRA) as? AuthenticatedUser
        }
        if (user == null) {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
            return
        }
        enableEdgeToEdge()

        setContent {
            val viewModel: PlayerProfileViewModel =
                viewModel(factory = PlayerProfileViewModelFactory(app.playerProfileService))

            PlayerProfileScreen(
                viewModel = viewModel,
                navigator = playerProfileNavigation,
                user = user
            )
        }
    }
}
