package com.eiprilcrim.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.eiprilcrim.R
import com.eiprilcrim.ui.theme.NavyCard
import com.eiprilcrim.ui.theme.NavySecondary
import com.eiprilcrim.ui.theme.PurpleAccent
import com.eiprilcrim.ui.theme.PurplePrimary
import com.eiprilcrim.ui.theme.TextPrimary
import com.eiprilcrim.ui.theme.TextSecondary

@Composable
fun QuizScreen() {
    var opcionSeleccionada by remember { mutableStateOf(-1) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(NavySecondary)
            .statusBarsPadding()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp, vertical = 20.dp)
    ) {
        Text(
            text = stringResource(R.string.modulo_quiz),
            color = TextPrimary,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = stringResource(R.string.modulo_quiz_desc),
            color = TextSecondary,
            fontSize = 14.sp
        )

        Spacer(modifier = Modifier.height(24.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = NavyCard),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(modifier = Modifier.padding(18.dp)) {
                Text(
                    text = "Pregunta 1",
                    color = PurpleAccent,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "¿Cuál es el propósito fundamental de la Cadena de Custodia?",
                    color = TextPrimary,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    lineHeight = 22.sp
                )

                Spacer(modifier = Modifier.height(16.dp))

                val opciones = listOf(
                    "Garantizar la autenticidad e integridad de la evidencia física.",
                    "Acelerar el proceso de detención del sospechoso.",
                    "Sustituir la toma de fotografías en el lugar de los hechos."
                )

                opciones.forEachIndexed { index, opcion ->
                    val esActivo = opcionSeleccionada == index
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                            .border(
                                width = 1.dp,
                                color = if (esActivo) PurpleAccent else PurplePrimary.copy(alpha = 0.2f),
                                shape = RoundedCornerShape(12.dp)
                            )
                            .clickable { opcionSeleccionada = index },
                        colors = CardDefaults.cardColors(
                            containerColor = if (esActivo) PurplePrimary.copy(alpha = 0.3f) else NavySecondary
                        ),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(
                            text = opcion,
                            color = if (esActivo) PurpleAccent else TextPrimary,
                            fontSize = 13.sp,
                            modifier = Modifier.padding(14.dp)
                        )
                    }
                }

                if (opcionSeleccionada == 0) {
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "¡Correcto! La cadena de custodia garantiza la integridad del indicio desde su hallazgo hasta la valoración del juez.",
                        color = PurpleAccent,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}
