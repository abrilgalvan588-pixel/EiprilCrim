package com.eiprilcrim.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector

data class BottomNavItem(
    val route: String,
    val title: String,
    val icon: ImageVector
)

val bottomNavItems = listOf(
    BottomNavItem(Screen.Dashboard.route, "Inicio", Icons.Default.Home),
    BottomNavItem(Screen.Casos.route, "Casos", Icons.Default.List),
    BottomNavItem(Screen.Glosario.route, "Glosario", Icons.Default.Book),
    BottomNavItem(Screen.Perfil.route, "Perfil", Icons.Default.Person)
)
