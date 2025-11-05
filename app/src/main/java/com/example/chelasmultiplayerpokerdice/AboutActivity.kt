package com.example.chelasmultiplayerpokerdice

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.chelasmultiplayerpokerdice.about.AboutNavigation
import com.example.chelasmultiplayerpokerdice.about.AboutScreen
import com.example.chelasmultiplayerpokerdice.about.AboutService
import com.example.chelasmultiplayerpokerdice.about.AboutFakeServiceImpl
import com.example.chelasmultiplayerpokerdice.lobbies.LobbiesViewModelFactory

class AboutActivity : ComponentActivity() {

    private val app by lazy { application as DependenciesContainer }
    // TODO ("VIEW MODEL ? ")

    private val aboutNavigation: AboutNavigation by lazy {
        NavigationIntentImpl(this)
    }

    private val aboutScreenService: AboutService = AboutFakeServiceImpl()


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