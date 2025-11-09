package com.example.chelasmultiplayerpokerdice.ui.theme.common

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GenericTopAppBar(
    title: String,
    modifier: Modifier = Modifier,
    onBackAction: (() -> Unit)? = null,
    actions: @Composable () -> Unit = { }
) {
    TopAppBar(
        title = { Text(title) },
        modifier = modifier,
        navigationIcon = {
            if (onBackAction != null) {
                IconButton(onClick = onBackAction) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Voltar"
                    )
                }
            }
        },
        actions = { actions() }
    )
}