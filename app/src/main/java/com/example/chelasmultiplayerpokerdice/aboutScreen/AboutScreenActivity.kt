package com.example.chelasmultiplayerpokerdice.aboutScreen

import AboutScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.chimp.home.AboutService
import com.example.chelasmultiplayerpokerdice.NavigationIntentImpl
import com.example.chimp.home.AboutServiceImpl


class AboutScreenActivity : ComponentActivity() {

    private val aboutScreenService: AboutService = AboutServiceImpl()
    private val aboutNavigation: AboutNavigation = NavigationIntentImpl


    override fun onCreate(savedInstanceState: Bundle?) {

         super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AboutScreen(
                service = aboutScreenService,
                navigator = aboutNavigation
            )
        }


    }


}