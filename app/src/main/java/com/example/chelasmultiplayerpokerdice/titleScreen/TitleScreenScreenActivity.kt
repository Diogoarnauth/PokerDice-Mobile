

// AboutScreen.kt
//package com.example.chelasmultiplayerpokerdice.aboutScreen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.chelasmultiplayerpokerdice.NavigationIntentImpl
import com.example.chelasmultiplayerpokerdice.titleScreen.TitleNavigation
import com.example.chimp.home.TitleScreenService
import com.example.chimp.home.TitleScreenServiceImpl


class TitleScreenActivity : ComponentActivity() {

    private val titleScreenService: TitleScreenService = TitleScreenServiceImpl()
    private val aboutNavigation: TitleNavigation by lazy {
        NavigationIntentImpl(this)
    }


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TitleScreen(
                service = titleScreenService,
                navigator = aboutNavigation
            )
        }


    }


}