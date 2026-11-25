package com.eiprilcrim

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import com.eiprilcrim.ui.theme.EpiCrimTheme
import com.eiprilcrim.ui.theme.NavyBackground
import com.eiprilcrim.ui.viewmodel.UsuarioViewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val appContainer = (application as EpiCrimApplication).container
        val usuarioViewModel = ViewModelProvider(
            this,
            UsuarioViewModel.Factory(appContainer.appModule.usuarioRepository)
        )[UsuarioViewModel::class.java]

        setContent {
            EpiCrimTheme {
                val state by usuarioViewModel.uiState.collectAsState()

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = NavyBackground
                ) {
                    val user = state.usuarioActual
                    if (user != null) {
                        DashboardScreen(
                            usuarioActual = user,
                            onCerrarSesion = {
                                usuarioViewModel.cerrarSesion()
                            }
                        )
                    } else {
                        PantallaPrincipal(
                            viewModel = usuarioViewModel
                        )
                    }
                }
            }
        }
    }
}
