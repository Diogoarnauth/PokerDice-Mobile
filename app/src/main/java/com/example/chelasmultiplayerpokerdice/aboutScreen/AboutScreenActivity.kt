package com.example.chelasmultiplayerpokerdice.aboutScreen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.chelasmultiplayerpokerdice.NavigationIntentImpl


class AboutScreenActivity : ComponentActivity() {

    private val aboutScreenService: AboutService = AboutServiceImpl()
    private val aboutNavigation: AboutNavigation by lazy {
        NavigationIntentImpl(this)
    }

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