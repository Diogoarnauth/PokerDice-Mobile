
// AboutScreen.kt
package com.example.chelasmultiplayerpokerdice.titleScreen

import TitleScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.chelasmultiplayerpokerdice.NavigationIntentImpl
import com.example.chimp.home.TitleScreenService
import com.example.chimp.home.TitleScreenServiceImpl


class TitleScreenActivity : ComponentActivity() {

    private val titleScreenService: TitleScreenService = TitleScreenServiceImpl()
    private val titleNavigation: TitleNavigation by lazy {
        NavigationIntentImpl(this)
    }


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TitleScreen(
                service = titleScreenService,
                navigator = titleNavigation
            )
        }


    }


}