package com.example.chelasmultiplayerpokerdice

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.chelasmultiplayerpokerdice.title.TitleNavigation
import com.example.chelasmultiplayerpokerdice.title.TitleScreen
import com.example.chelasmultiplayerpokerdice.title.TitleViewModel
import com.example.chelasmultiplayerpokerdice.title.TitleViewModelFactory

class TitleActivity : ComponentActivity() {

    private val app by lazy { application as DependenciesContainer }
    private val titleNavigation: TitleNavigation by lazy {
        NavigationIntentImpl(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {

            val viewModel: TitleViewModel =
                viewModel(factory = TitleViewModelFactory(app.titleService))

            TitleScreen(
                viewModel = viewModel,
                navigator = titleNavigation
            )
        }
    }
}