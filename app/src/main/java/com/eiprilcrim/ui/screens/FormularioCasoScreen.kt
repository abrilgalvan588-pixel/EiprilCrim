package com.eiprilcrim.ui.screens

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
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.eiprilcrim.ui.theme.EpiCrimTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormularioCasoScreen(
    casoId: Int? = null,
    onVolver: () -> Unit = {},
    onGuardarCaso: () -> Unit = {}
) {
    var titulo by remember { mutableStateOf("") }
    var nivelSeleccionado by remember { mutableStateOf("Fácil") }
    var descripcionCorta by remember { mutableStateOf("") }
    var contextoCaso by remember { mutableStateOf("") }
    var evidenciasSimuladas by remember { mutableStateOf("") }
    var preguntasAnalisis by remember { mutableStateOf("") }

    var mensajeError by remember { mutableStateOf("") }

    val niveles = listOf("Fácil", "Medio", "Difícil")

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = if (casoId != null) "Editar caso" else "Nuevo caso",
                        color = MaterialTheme.colorScheme.onSurface,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onVolver) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Retroceder",
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .statusBarsPadding()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp, vertical = 12.dp)
        ) {
            // Título
            OutlinedTextField(
                value = titulo,
                onValueChange = {
                    titulo = it
                    mensajeError = ""
                },
                label = { Text("Título") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = coloresCamposFormulario()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Selector Nivel
            Text(
                text = "Nivel del caso",
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                niveles.forEach { nivel ->
                    val esSeleccionado = nivelSeleccionado == nivel
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(42.dp)
                            .background(
                                color = if (esSeleccionado) MaterialTheme.colorScheme.primaryContainer
                                else MaterialTheme.colorScheme.surface,
                                shape = RoundedCornerShape(10.dp)
                            )
                            .border(
                                width = 1.dp,
                                color = if (esSeleccionado) MaterialTheme.colorScheme.primary
                                else MaterialTheme.colorScheme.outline.copy(alpha = 0.4f),
                                shape = RoundedCornerShape(10.dp)
                            )
                            .clickable { nivelSeleccionado = nivel },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = nivel,
                            color = if (esSeleccionado) MaterialTheme.colorScheme.primary
                            else MaterialTheme.colorScheme.onSurface,
                            fontSize = 13.sp,
                            fontWeight = if (esSeleccionado) FontWeight.Bold else FontWeight.Normal
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Descripción corta
            OutlinedTextField(
                value = descripcionCorta,
                onValueChange = {
                    descripcionCorta = it
                    mensajeError = ""
                },
                label = { Text("Descripción corta") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = coloresCamposFormulario()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Contexto del caso
            OutlinedTextField(
                value = contextoCaso,
                onValueChange = {
                    contextoCaso = it
                    mensajeError = ""
                },
                label = { Text("Contexto del caso") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3,
                shape = RoundedCornerShape(12.dp),
                colors = coloresCamposFormulario()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Evidencias simuladas
            OutlinedTextField(
                value = evidenciasSimuladas,
                onValueChange = {
                    evidenciasSimuladas = it
                    mensajeError = ""
                },
                label = { Text("Evidencias simuladas") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 2,
                shape = RoundedCornerShape(12.dp),
                colors = coloresCamposFormulario()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Preguntas de análisis
            OutlinedTextField(
                value = preguntasAnalisis,
                onValueChange = {
                    preguntasAnalisis = it
                    mensajeError = ""
                },
                label = { Text("Preguntas de análisis") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 2,
                shape = RoundedCornerShape(12.dp),
                colors = coloresCamposFormulario()
            )

            if (mensajeError.isNotEmpty()) {
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = mensajeError,
                    color = MaterialTheme.colorScheme.error,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium
                )
            }

            Spacer(modifier = Modifier.height(28.dp))

            // Botón Lila "Guardar caso"
            Button(
                onClick = {
                    if (titulo.isBlank() ||
                        descripcionCorta.isBlank() ||
                        contextoCaso.isBlank() ||
                        evidenciasSimuladas.isBlank() ||
                        preguntasAnalisis.isBlank()
                    ) {
                        mensajeError = "Completa todos los campos obligatorios."
                    } else {
                        onGuardarCaso()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = CircleShape,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                )
            ) {
                Text(
                    text = "Guardar caso",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Composable
private fun coloresCamposFormulario() = OutlinedTextFieldDefaults.colors(
    focusedBorderColor = MaterialTheme.colorScheme.primary,
    unfocusedBorderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f),
    focusedLabelColor = MaterialTheme.colorScheme.primary,
    unfocusedLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
    focusedTextColor = MaterialTheme.colorScheme.onSurface,
    unfocusedTextColor = MaterialTheme.colorScheme.onSurface
)

@Preview(showBackground = true)
@Composable
fun FormularioCasoScreenPreview() {
    EpiCrimTheme {
        FormularioCasoScreen()
    }
}
