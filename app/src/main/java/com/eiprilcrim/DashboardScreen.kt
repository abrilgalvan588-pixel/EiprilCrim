package com.eiprilcrim

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.eiprilcrim.domain.model.Usuario
import com.eiprilcrim.ui.theme.NavyCard
import com.eiprilcrim.ui.theme.NavySecondary
import com.eiprilcrim.ui.theme.PurpleAccent
import com.eiprilcrim.ui.theme.PurplePrimary
import com.eiprilcrim.ui.theme.TextPrimary
import com.eiprilcrim.ui.theme.TextSecondary

@Composable
fun DashboardScreen(
    usuarioActual: Usuario? = null,
    onCerrarSesion: () -> Unit = {},
    onAbrirSeccion: (String) -> Unit = {}
) {
    var busqueda by remember { mutableStateOf("") }
    var tabSeleccionada by remember { mutableIntStateOf(0) }

    val textoCasos = stringResource(R.string.modulo_casos)
    val textoGlosario = stringResource(R.string.modulo_glosario)
    val textoProtocolos = stringResource(R.string.modulo_protocolos)
    val textoQuiz = stringResource(R.string.modulo_quiz)
    val textoPerfil = stringResource(R.string.nav_perfil)

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = NavySecondary,
        bottomBar = {
            NavigationBar(
                containerColor = Color(0xFF191D24),
                contentColor = TextPrimary
            ) {
                NavigationBarItem(
                    selected = tabSeleccionada == 0,
                    onClick = { tabSeleccionada = 0 },
                    icon = { Text("★", fontSize = 18.sp) },
                    label = { Text(stringResource(R.string.nav_inicio), fontSize = 11.sp) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = TextPrimary,
                        selectedTextColor = TextPrimary,
                        indicatorColor = PurplePrimary.copy(alpha = 0.35f),
                        unselectedIconColor = TextSecondary,
                        unselectedTextColor = TextSecondary
                    )
                )
                NavigationBarItem(
                    selected = tabSeleccionada == 1,
                    onClick = {
                        tabSeleccionada = 1
                        onAbrirSeccion(textoCasos)
                    },
                    icon = { Text("▤", fontSize = 18.sp) },
                    label = { Text(textoCasos, fontSize = 11.sp) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = TextPrimary,
                        selectedTextColor = TextPrimary,
                        indicatorColor = PurplePrimary.copy(alpha = 0.35f),
                        unselectedIconColor = TextSecondary,
                        unselectedTextColor = TextSecondary
                    )
                )
                NavigationBarItem(
                    selected = tabSeleccionada == 2,
                    onClick = {
                        tabSeleccionada = 2
                        onAbrirSeccion(textoGlosario)
                    },
                    icon = { Text("☰", fontSize = 18.sp) },
                    label = { Text(textoGlosario, fontSize = 11.sp) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = TextPrimary,
                        selectedTextColor = TextPrimary,
                        indicatorColor = PurplePrimary.copy(alpha = 0.35f),
                        unselectedIconColor = TextSecondary,
                        unselectedTextColor = TextSecondary
                    )
                )
                NavigationBarItem(
                    selected = tabSeleccionada == 3,
                    onClick = {
                        tabSeleccionada = 3
                        onAbrirSeccion(textoPerfil)
                    },
                    icon = { Text("◉", fontSize = 18.sp) },
                    label = { Text(textoPerfil, fontSize = 11.sp) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = TextPrimary,
                        selectedTextColor = TextPrimary,
                        indicatorColor = PurplePrimary.copy(alpha = 0.35f),
                        unselectedIconColor = TextSecondary,
                        unselectedTextColor = TextSecondary
                    )
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .statusBarsPadding()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp, vertical = 16.dp)
        ) {
            // Encabezado con saludo personalizado desde la BD
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Hola, ${usuarioActual?.nombre ?: "Abril"}",
                    color = TextPrimary,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .border(1.dp, TextSecondary.copy(alpha = 0.5f), CircleShape)
                        .clickable { tabSeleccionada = 3 },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = usuarioActual?.nombre?.take(1)?.uppercase() ?: "A",
                        color = TextPrimary,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // Buscador
            OutlinedTextField(
                value = busqueda,
                onValueChange = { busqueda = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = {
                    Text(
                        text = stringResource(R.string.buscar_placeholder),
                        color = TextSecondary,
                        fontSize = 14.sp
                    )
                },
                leadingIcon = {
                    Text(
                        text = "⌕",
                        color = TextSecondary,
                        fontSize = 18.sp
                    )
                },
                singleLine = true,
                shape = CircleShape,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color(0xFF23272F),
                    unfocusedContainerColor = Color(0xFF23272F),
                    focusedTextColor = TextPrimary,
                    unfocusedTextColor = TextPrimary,
                    focusedBorderColor = PurpleAccent,
                    unfocusedBorderColor = Color.Transparent,
                    cursorColor = PurpleAccent
                )
            )

            Spacer(modifier = Modifier.height(24.dp))

            when (tabSeleccionada) {
                0 -> {
                    // Pantalla Principal / Inicio del Dashboard
                    Text(
                        text = stringResource(R.string.laboratorio_titulo),
                        color = PurpleAccent,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold,
                        letterSpacing = 1.sp
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = stringResource(R.string.dashboard_encabezado),
                        color = TextPrimary,
                        fontSize = 26.sp,
                        fontWeight = FontWeight.Bold,
                        lineHeight = 32.sp
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = stringResource(R.string.dashboard_subtitulo),
                        color = TextSecondary,
                        fontSize = 13.sp,
                        lineHeight = 18.sp
                    )

                    Spacer(modifier = Modifier.height(22.dp))

                    // Módulos principales
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(14.dp)
                    ) {
                        TarjetaModulo(
                            titulo = textoCasos,
                            subtitulo = stringResource(R.string.modulo_casos_desc),
                            simbolo = "▤",
                            modifier = Modifier.weight(1f),
                            onClick = { onAbrirSeccion(textoCasos) }
                        )
                        TarjetaModulo(
                            titulo = textoGlosario,
                            subtitulo = stringResource(R.string.modulo_glosario_desc),
                            simbolo = "☰",
                            modifier = Modifier.weight(1f),
                            onClick = { onAbrirSeccion(textoGlosario) }
                        )
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(14.dp)
                    ) {
                        TarjetaModulo(
                            titulo = textoProtocolos,
                            subtitulo = stringResource(R.string.modulo_protocolos_desc),
                            simbolo = "❖",
                            modifier = Modifier.weight(1f),
                            onClick = { onAbrirSeccion(textoProtocolos) }
                        )
                        TarjetaModulo(
                            titulo = textoQuiz,
                            subtitulo = stringResource(R.string.modulo_quiz_desc),
                            simbolo = "◈",
                            modifier = Modifier.weight(1f),
                            onClick = { onAbrirSeccion(textoQuiz) }
                        )
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "🛡",
                            color = PurpleAccent,
                            fontSize = 15.sp
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = stringResource(R.string.pie_dashboard),
                            color = TextSecondary,
                            fontSize = 11.sp,
                            lineHeight = 15.sp
                        )
                    }
                }

                1 -> {
                    // Pestaña Casos
                    Text(
                        text = textoCasos,
                        color = TextPrimary,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = stringResource(R.string.modulo_casos_desc),
                        color = TextSecondary,
                        fontSize = 13.sp
                    )
                }

                2 -> {
                    // Pestaña Glosario
                    Text(
                        text = textoGlosario,
                        color = TextPrimary,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = stringResource(R.string.modulo_glosario_desc),
                        color = TextSecondary,
                        fontSize = 13.sp
                    )
                }

                3 -> {
                    // Pestaña Perfil conectada a los datos del Usuario en Room
                    Text(
                        text = stringResource(R.string.nav_perfil),
                        color = TextPrimary,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = NavyCard),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Column(modifier = Modifier.padding(20.dp)) {
                            Text(
                                text = usuarioActual?.nombre ?: "Usuario",
                                color = TextPrimary,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = usuarioActual?.correo ?: "correo@ejemplo.com",
                                color = TextSecondary,
                                fontSize = 14.sp
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(28.dp))

                    Button(
                        onClick = onCerrarSesion,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD32F2F)),
                        shape = CircleShape
                    ) {
                        Text(
                            text = stringResource(R.string.cerrar_sesion),
                            color = Color.White,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun TarjetaModulo(
    titulo: String,
    subtitulo: String,
    simbolo: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Column(
        modifier = modifier
            .height(135.dp)
            .background(
                color = NavyCard,
                shape = RoundedCornerShape(18.dp)
            )
            .border(
                width = 1.dp,
                color = PurplePrimary.copy(alpha = 0.25f),
                shape = RoundedCornerShape(18.dp)
            )
            .clickable { onClick() }
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = simbolo,
            color = PurpleAccent,
            fontSize = 20.sp
        )

        Column {
            Text(
                text = titulo,
                color = TextPrimary,
                fontSize = 17.sp,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(3.dp))
            Text(
                text = subtitulo,
                color = TextSecondary,
                fontSize = 11.sp
            )
        }
    }
}
