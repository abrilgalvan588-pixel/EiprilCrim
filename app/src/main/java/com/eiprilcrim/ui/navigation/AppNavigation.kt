package com.eiprilcrim.ui.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import com.eiprilcrim.ui.screens.CasosScreen
import com.eiprilcrim.ui.screens.DetalleCasoScreen
import com.eiprilcrim.ui.screens.GlosarioScreen
import com.eiprilcrim.ui.screens.PerfilScreen
import com.eiprilcrim.ui.screens.ProtocolosScreen
import com.eiprilcrim.ui.screens.QuizScreen
import com.eiprilcrim.ui.theme.NavySecondary
import com.eiprilcrim.ui.theme.PurpleAccent
import com.eiprilcrim.ui.theme.TextPrimary
import com.eiprilcrim.ui.theme.TextSecondary
import com.eiprilcrim.ui.viewmodel.UsuarioViewModel

@Composable
fun AppNavigation(
    usuarioViewModel: UsuarioViewModel
) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val state by usuarioViewModel.uiState.collectAsState()

    // Ocultar barra inferior en Login y en Detalle de Caso
    val mostrarBottomBar = when {
        currentRoute == null -> false
        currentRoute == Screen.Login.route -> false
        currentRoute.startsWith("detalle_caso") -> false
        else -> true
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = NavySecondary,
        bottomBar = {
            if (mostrarBottomBar) {
                NavigationBar(
                    containerColor = Color(0xFF191D24),
                    contentColor = TextPrimary
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
                                selectedIconColor = TextPrimary,
                                selectedTextColor = TextPrimary,
                                indicatorColor = PurpleAccent, // Ítem activo resaltado en lila
                                unselectedIconColor = TextSecondary,
                                unselectedTextColor = TextSecondary
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
                    }
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
                QuizScreen()
            }

            composable(Screen.Perfil.route) {
                PerfilScreen(
                    usuarioActual = state.usuarioActual,
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
