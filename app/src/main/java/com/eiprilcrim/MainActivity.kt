package com.eiprilcrim

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import com.eiprilcrim.ui.navigation.AppNavigation
import com.eiprilcrim.ui.theme.EpiCrimTheme
import com.eiprilcrim.ui.viewmodel.ThemeViewModel
import com.eiprilcrim.ui.viewmodel.UsuarioViewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val appContainer = (application as EpiCrimApplication).container

        val usuarioViewModel = ViewModelProvider(
            this,
            UsuarioViewModel.Factory(appContainer.appModule.usuarioRepository)
        )[UsuarioViewModel::class.java]

        val themeViewModel = ViewModelProvider(
            this,
            ThemeViewModel.Factory(appContainer.appModule.settingsRepository)
        )[ThemeViewModel::class.java]

        setContent {
            val isLightModeEnabled by themeViewModel.isLightModeEnabled.collectAsState()

            enableEdgeToEdge(
                statusBarStyle = if (isLightModeEnabled) {
                    SystemBarStyle.light(
                        Color.TRANSPARENT,
                        Color.TRANSPARENT
                    )
                } else {
                    SystemBarStyle.dark(
                        Color.TRANSPARENT
                    )
                }
            )

            EpiCrimTheme(darkTheme = !isLightModeEnabled) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavigation(
                        usuarioViewModel = usuarioViewModel,
                        themeViewModel = themeViewModel,
                        casoRepository = appContainer.appModule.casoRepository,
                        glosarioRepository = appContainer.appModule.glosarioRepository,
                        protocoloRepository = appContainer.appModule.protocoloRepository,
                        resultadoQuizRepository = appContainer.appModule.resultadoQuizRepository
                    )
                }
            }
        }
    }
}
