package com.eiprilcrim.ui.navigation

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Bienvenida : Screen("bienvenida")
    object Dashboard : Screen("dashboard")
    object Casos : Screen("casos")
    object DetalleCaso : Screen("detalle_caso/{casoId}") {
        fun createRoute(casoId: Int) = "detalle_caso/$casoId"
    }
    object FormularioCaso : Screen("formulario_caso?casoId={casoId}") {
        fun createRoute(casoId: Int? = null) = if (casoId != null) "formulario_caso?casoId=$casoId" else "formulario_caso"
    }
    object Glosario : Screen("glosario")
    object Protocolos : Screen("protocolos")
    object Quiz : Screen("quiz")
    object ResultadoQuiz : Screen("resultado_quiz")
    object Perfil : Screen("perfil")
}
