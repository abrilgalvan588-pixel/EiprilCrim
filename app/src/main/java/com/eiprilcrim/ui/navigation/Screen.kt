package com.eiprilcrim.ui.navigation

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Dashboard : Screen("dashboard")
    object Casos : Screen("casos")
    object DetalleCaso : Screen("detalle_caso/{casoId}") {
        fun createRoute(casoId: Int) = "detalle_caso/$casoId"
    }
    object Glosario : Screen("glosario")
    object Protocolos : Screen("protocolos")
    object Quiz : Screen("quiz")
    object Perfil : Screen("perfil")
}
