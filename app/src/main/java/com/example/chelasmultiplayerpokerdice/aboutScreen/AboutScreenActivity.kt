package com.example.chelasmultiplayerpokerdice.aboutScreen

import AboutScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import com.example.chimp.home.AboutService

// AboutScreen.kt
package com.example.chelasmultiplayerpokerdice.aboutScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier

import com.example.chimp.home.AboutServiceImpl

class AboutScreenActivity : ComponentActivity() {

    private val aboutScreenService: AboutService = AboutServiceImpl()
    private val aboutNavigation: AboutNavigation = AboutNavigationImpl()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AboutScreen(
                service = aboutScreenService,
                navigator = aboutNavigation.goToTitleScreen()
            )
        }
    }
}