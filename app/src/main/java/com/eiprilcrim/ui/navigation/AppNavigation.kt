package com.eiprilcrim.ui.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
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
import androidx.compose.runtime.rememberCoroutineScope
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
import com.eiprilcrim.domain.model.ResultadoQuiz
import com.eiprilcrim.domain.repository.CasoRepository
import com.eiprilcrim.domain.repository.GlosarioRepository
import com.eiprilcrim.domain.repository.ProtocoloRepository
import com.eiprilcrim.domain.repository.ResultadoQuizRepository
import com.eiprilcrim.ui.screens.BienvenidaScreen
import com.eiprilcrim.ui.screens.CasosScreen
import com.eiprilcrim.ui.screens.DetalleCasoScreen
import com.eiprilcrim.ui.screens.FormularioCasoScreen
import com.eiprilcrim.ui.screens.GlosarioScreen
import com.eiprilcrim.ui.screens.PerfilScreen
import com.eiprilcrim.ui.screens.ProtocolosScreen
import com.eiprilcrim.ui.screens.QuizScreen
import com.eiprilcrim.ui.screens.ResultadoQuizScreen
import com.eiprilcrim.ui.utils.rememberIsLandscape
import com.eiprilcrim.ui.viewmodel.ThemeViewModel
import com.eiprilcrim.ui.viewmodel.UsuarioViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun AppNavigation(
    usuarioViewModel: UsuarioViewModel,
    themeViewModel: ThemeViewModel,
    casoRepository: CasoRepository? = null,
    glosarioRepository: GlosarioRepository? = null,
    protocoloRepository: ProtocoloRepository? = null,
    resultadoQuizRepository: ResultadoQuizRepository? = null
) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val state by usuarioViewModel.uiState.collectAsState()
    val isLightModeEnabled by themeViewModel.isLightModeEnabled.collectAsState()
    val scope = rememberCoroutineScope()

    val isLandscape = rememberIsLandscape()

    // Ocultar la barra inferior en horizontal (landscape), Bienvenida, Formulario, Resultado, Login y Detalle de Caso
    val mostrarBottomBar = when {
        currentRoute == null -> false
        isLandscape -> false
        currentRoute == Screen.Login.route -> false
        currentRoute == Screen.Bienvenida.route -> false
        currentRoute.startsWith("formulario_caso") -> false
        currentRoute.startsWith("resultado_quiz") -> false
        currentRoute.startsWith("detalle_caso") -> false
        else -> true
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background,
        bottomBar = {
            AnimatedVisibility(
                visible = mostrarBottomBar,
                enter = fadeIn() + slideInVertically { it },
                exit = fadeOut() + slideOutVertically { it }
            ) {
                NavigationBar(
                    containerColor = MaterialTheme.colorScheme.surface,
                    contentColor = MaterialTheme.colorScheme.onSurface
                ) {
                    bottomNavItems.forEach { item ->
                        val selected = currentRoute == item.route
                        NavigationBarItem(
                            selected = selected,
                            onClick = {
                                navController.navigate(item.route) {
                                    popUpTo(Screen.Dashboard.route) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
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
                    casoRepository = casoRepository,
                    onCasoClick = { casoId ->
                        navController.navigate(Screen.DetalleCaso.createRoute(casoId))
                    },
                    onNuevoCasoClick = {
                        navController.navigate(Screen.FormularioCaso.createRoute())
                    },
                    onVolver = {
                        navController.navigate(Screen.Dashboard.route) {
                            popUpTo(Screen.Dashboard.route) { inclusive = true }
                        }
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
                    casoRepository = casoRepository,
                    onVolver = { navController.popBackStack() },
                    onIniciarAnalisis = { id ->
                        navController.navigate(Screen.Quiz.createRoute(id))
                    }
                )
            }

            composable(Screen.Glosario.route) {
                GlosarioScreen(
                    tabInicial = 0,
                    glosarioRepository = glosarioRepository,
                    protocoloRepository = protocoloRepository
                )
            }

            composable(Screen.Protocolos.route) {
                GlosarioScreen(
                    tabInicial = 1,
                    glosarioRepository = glosarioRepository,
                    protocoloRepository = protocoloRepository
                )
            }

            composable(
                route = Screen.Quiz.route,
                arguments = listOf(navArgument("casoId") { type = NavType.IntType; defaultValue = -1 })
            ) { backStackEntry ->
                val idArg = backStackEntry.arguments?.getInt("casoId") ?: -1
                val casoId = if (idArg != -1) idArg else null
                QuizScreen(
                    casoId = casoId,
                    onVolver = { navController.popBackStack() },
                    onQuizFinalizado = { correctas, total ->
                        val idUsuario = state.usuarioActual?.idUsuario ?: 0
                        val fecha = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).format(Date())
                        val porcentaje = if (total > 0) (correctas * 100) / total else 0

                        scope.launch {
                            resultadoQuizRepository?.guardarResultado(
                                ResultadoQuiz(
                                    idUsuario = idUsuario,
                                    correctas = correctas,
                                    total = total,
                                    porcentaje = porcentaje,
                                    fecha = fecha
                                )
                            )
                        }

                        navController.navigate(Screen.ResultadoQuiz.createRoute(correctas, total))
                    }
                )
            }

            composable(
                route = Screen.ResultadoQuiz.route,
                arguments = listOf(
                    navArgument("correctas") { type = NavType.IntType; defaultValue = 4 },
                    navArgument("total") { type = NavType.IntType; defaultValue = 5 }
                )
            ) { backStackEntry ->
                val correctas = backStackEntry.arguments?.getInt("correctas") ?: 4
                val total = backStackEntry.arguments?.getInt("total") ?: 5
                ResultadoQuizScreen(
                    correctas = correctas,
                    total = total,
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
                    resultadoQuizRepository = resultadoQuizRepository,
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
