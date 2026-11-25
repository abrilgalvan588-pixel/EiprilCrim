package com.eiprilcrim.ui.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.eiprilcrim.DashboardScreen
import com.eiprilcrim.PantallaPrincipal
import com.eiprilcrim.ui.screens.BienvenidaScreen
import com.eiprilcrim.ui.screens.CasosScreen
import com.eiprilcrim.ui.screens.DetalleCasoScreen
import com.eiprilcrim.ui.screens.FormularioCasoScreen
import com.eiprilcrim.ui.screens.GlosarioScreen
import com.eiprilcrim.ui.screens.PerfilScreen
import com.eiprilcrim.ui.screens.ProtocolosScreen
import com.eiprilcrim.ui.screens.QuizScreen
import com.eiprilcrim.ui.screens.ResultadoQuizScreen
import com.eiprilcrim.ui.viewmodel.ThemeViewModel
import com.eiprilcrim.ui.viewmodel.UsuarioViewModel

@Composable
fun AppNavigation(
    usuarioViewModel: UsuarioViewModel,
    themeViewModel: ThemeViewModel
) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val state by usuarioViewModel.uiState.collectAsState()
    val isLightModeEnabled by themeViewModel.isLightModeEnabled.collectAsState()

    // Ocultar la barra inferior en Bienvenida, Formulario, Resultado, Login y Detalle de Caso
    val mostrarBottomBar = when {
        currentRoute == null -> false
        currentRoute == Screen.Login.route -> false
        currentRoute == Screen.Bienvenida.route -> false
        currentRoute.startsWith("formulario_caso") -> false
        currentRoute == Screen.ResultadoQuiz.route -> false
        currentRoute.startsWith("detalle_caso") -> false
        else -> true
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background,
        bottomBar = {
            if (mostrarBottomBar) {
                NavigationBar(
                    containerColor = MaterialTheme.colorScheme.surface,
                    contentColor = MaterialTheme.colorScheme.onSurface
                ) {
                    bottomNavItems.forEach { item ->
                        val selected = currentRoute == item.route
                        NavigationBarItem(
                            selected = selected,
                            onClick = {
                                if (currentRoute != item.route) {
                                    navController.navigate(item.route) {
                                        popUpTo(navController.graph.findStartDestination().id) {
                                            saveState = true
                                        }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                }
                            },
                            icon = {
                                Icon(
                                    imageVector = item.icon,
                                    contentDescription = item.title
                                )
                            },
                            label = { Text(item.title, fontSize = 11.sp) },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = MaterialTheme.colorScheme.primary,
                                selectedTextColor = MaterialTheme.colorScheme.primary,
                                indicatorColor = MaterialTheme.colorScheme.primaryContainer,
                                unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = if (state.usuarioActual != null) Screen.Dashboard.route else Screen.Login.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Login.route) {
                PantallaPrincipal(
                    viewModel = usuarioViewModel,
                    onIniciarSesion = {
                        navController.navigate(Screen.Bienvenida.route)
                    }
                )
            }

            composable(Screen.Bienvenida.route) {
                BienvenidaScreen(
                    onComenzarClick = {
                        navController.navigate(Screen.Dashboard.route) {
                            popUpTo(Screen.Login.route) { inclusive = true }
                        }
                    }
                )
            }

            composable(Screen.Dashboard.route) {
                DashboardScreen(
                    usuarioActual = state.usuarioActual,
                    onAbrirSeccion = { ruta ->
                        when (ruta) {
                            "Casos" -> navController.navigate(Screen.Casos.route)
                            "Glosario" -> navController.navigate(Screen.Glosario.route)
                            "Protocolos" -> navController.navigate(Screen.Protocolos.route)
                            "Quiz" -> navController.navigate(Screen.Quiz.route)
                            "Perfil" -> navController.navigate(Screen.Perfil.route)
                            else -> navController.navigate(Screen.Casos.route)
                        }
                    },
                    onCerrarSesion = {
                        usuarioViewModel.cerrarSesion()
                        navController.navigate(Screen.Login.route) {
                            popUpTo(0) { inclusive = true }
                        }
                    }
                )
            }

            composable(Screen.Casos.route) {
                CasosScreen(
                    onCasoClick = { casoId ->
                        navController.navigate(Screen.DetalleCaso.createRoute(casoId))
                    },
                    onNuevoCasoClick = {
                        navController.navigate(Screen.FormularioCaso.createRoute())
                    }
                )
            }

            composable(
                route = Screen.FormularioCaso.route,
                arguments = listOf(navArgument("casoId") {
                    type = NavType.IntType
                    defaultValue = -1
                })
            ) { backStackEntry ->
                val idArg = backStackEntry.arguments?.getInt("casoId") ?: -1
                val casoId = if (idArg != -1) idArg else null
                FormularioCasoScreen(
                    casoId = casoId,
                    onVolver = { navController.popBackStack() },
                    onGuardarCaso = { navController.popBackStack() }
                )
            }

            composable(
                route = Screen.DetalleCaso.route,
                arguments = listOf(navArgument("casoId") { type = NavType.IntType })
            ) { backStackEntry ->
                val casoId = backStackEntry.arguments?.getInt("casoId") ?: 1
                DetalleCasoScreen(
                    casoId = casoId,
                    onVolver = { navController.popBackStack() }
                )
            }

            composable(Screen.Glosario.route) {
                GlosarioScreen()
            }

            composable(Screen.Protocolos.route) {
                ProtocolosScreen()
            }

            composable(Screen.Quiz.route) {
                QuizScreen(
                    onQuizFinalizado = {
                        navController.navigate(Screen.ResultadoQuiz.route)
                    }
                )
            }

            composable(Screen.ResultadoQuiz.route) {
                ResultadoQuizScreen(
                    onReintentar = {
                        navController.navigate(Screen.Quiz.route) {
                            popUpTo(Screen.ResultadoQuiz.route) { inclusive = true }
                        }
                    },
                    onVolverInicio = {
                        navController.navigate(Screen.Dashboard.route) {
                            popUpTo(Screen.Dashboard.route) { inclusive = true }
                        }
                    }
                )
            }

            composable(Screen.Perfil.route) {
                PerfilScreen(
                    usuarioActual = state.usuarioActual,
                    isLightModeEnabled = isLightModeEnabled,
                    onLightModeChange = { enabled ->
                        themeViewModel.setLightModeEnabled(enabled)
                    },
                    onCerrarSesion = {
                        usuarioViewModel.cerrarSesion()
                        navController.navigate(Screen.Login.route) {
                            popUpTo(0) { inclusive = true }
                        }
                    }
                )
            }
        }
    }
}
