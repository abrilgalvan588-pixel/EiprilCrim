package com.eiprilcrim.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.eiprilcrim.R

data class PreguntaQuiz(
    val id: Int,
    val pregunta: String,
    val opciones: List<String>,
    val respuestaCorrecta: Int,
    val explicacion: String
)

val preguntasQuizDemo = listOf(
    PreguntaQuiz(
        id = 1,
        pregunta = "¿Cuál es el propósito fundamental de la Cadena de Custodia?",
        opciones = listOf(
            "Garantizar la autenticidad e integridad de la evidencia física.",
            "Acelerar el proceso de detención del sospechoso.",
            "Sustituir la toma de fotografías en el lugar de los hechos.",
            "Eliminar la necesidad de dictámenes periciales."
        ),
        respuestaCorrecta = 0,
        explicacion = "La cadena de custodia asegura la trazabilidad y preservación del indicio desde su hallazgo hasta el juicio."
    ),
    PreguntaQuiz(
        id = 2,
        pregunta = "¿Qué disciplina se encarga del estudio y clasificación de huellas dactilares?",
        opciones = listOf(
            "Balística Forense",
            "Dactiloscopía",
            "Documentoscopía",
            "Toxicología Forense"
        ),
        respuestaCorrecta = 1,
        explicacion = "La dactiloscopía analiza los dibujos papilares de las yemas de los dedos para la identificación humana."
    ),
    PreguntaQuiz(
        id = 3,
        pregunta = "¿Cuál es la primera acción requerida al arribar al lugar de los hechos?",
        opciones = listOf(
            "Preservar y acordonar la escena del crimen",
            "Entrevistar a los medios de comunicación",
            "Recolectar rápidamente todos los objetos",
            "Limpiar las manchas biológicas"
        ),
        respuestaCorrecta = 0,
        explicacion = "Acordonar la escena evita la contaminación, pérdida o alteración de los indicios por personas ajenas."
    ),
    PreguntaQuiz(
        id = 4,
        pregunta = "¿Qué constituye un 'indicio' en la investigación criminológica?",
        opciones = listOf(
            "Todo elemento material sensible relacionado con el hecho investigado",
            "Una suposición o rumor sin sustento técnico",
            "La sentencia dictada por el juez de control",
            "La declaración pública del fiscal"
        ),
        respuestaCorrecta = 0,
        explicacion = "Un indicio es cualquier objeto, marca o señal que permite inferir la comisión de un delito."
    ),
    PreguntaQuiz(
        id = 5,
        pregunta = "¿Cómo se debe embalar correctamente una muestra biológica húmeda?",
        opciones = listOf(
            "Secar al aire a temperatura ambiente y guardar en bolsa de papel",
            "Guardar en bolsa plástica hermética sin secar",
            "Aplicar calor directo con secador y plástico",
            "Sumergir en solución desinfectante"
        ),
        respuestaCorrecta = 0,
        explicacion = "El secado ambiental y el empaque en papel evitan la proliferación de bacterias y hongos que degradan el ADN."
    )
)

@Composable
fun QuizScreen(
    onQuizFinalizado: (correctas: Int, total: Int) -> Unit = { _, _ -> }
) {
    var indicePregunta by rememberSaveable { mutableIntStateOf(0) }
    var opcionSeleccionada by rememberSaveable { mutableIntStateOf(-1) }
    var respuestasCorrectas by rememberSaveable { mutableIntStateOf(0) }
    var mensajeValidacion by rememberSaveable { mutableStateOf("") }

    val preguntaActual = preguntasQuizDemo[indicePregunta]
    val totalPreguntas = preguntasQuizDemo.size
    val progreso = (indicePregunta + 1).toFloat() / totalPreguntas.toFloat()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .statusBarsPadding()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp, vertical = 20.dp)
    ) {
        Text(
            text = stringResource(R.string.modulo_quiz),
            color = MaterialTheme.colorScheme.onBackground,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = stringResource(R.string.modulo_quiz_desc),
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontSize = 14.sp
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Barra de progreso "Pregunta X de Y"
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Pregunta ${indicePregunta + 1} de $totalPreguntas",
                color = MaterialTheme.colorScheme.primary,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "${(progreso * 100).toInt()}%",
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontSize = 13.sp
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        LinearProgressIndicator(
            progress = { progreso },
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp),
            color = MaterialTheme.colorScheme.primary,
            trackColor = MaterialTheme.colorScheme.primaryContainer,
            gapSize = 0.dp,
            drawStopIndicator = {}
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Tarjeta con la pregunta y opciones
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            shape = RoundedCornerShape(18.dp)
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Text(
                    text = preguntaActual.pregunta,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    lineHeight = 24.sp
                )

                Spacer(modifier = Modifier.height(18.dp))

                // Renderizar 4 Opciones con retroalimentación inmediata
                preguntaActual.opciones.forEachIndexed { index, opcionText ->
                    val esSeleccionada = opcionSeleccionada == index
                    val esCorrecta = index == preguntaActual.respuestaCorrecta

                    val colorBorde = when {
                        opcionSeleccionada == -1 -> MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
                        esSeleccionada && esCorrecta -> MaterialTheme.colorScheme.primary
                        esSeleccionada && !esCorrecta -> MaterialTheme.colorScheme.error
                        esCorrecta && opcionSeleccionada != -1 -> MaterialTheme.colorScheme.primary
                        else -> MaterialTheme.colorScheme.outline.copy(alpha = 0.2f)
                    }

                    val colorFondo = when {
                        opcionSeleccionada == -1 -> MaterialTheme.colorScheme.surface
                        esSeleccionada && esCorrecta -> MaterialTheme.colorScheme.primaryContainer
                        esSeleccionada && !esCorrecta -> MaterialTheme.colorScheme.errorContainer
                        esCorrecta && opcionSeleccionada != -1 -> MaterialTheme.colorScheme.primaryContainer
                        else -> MaterialTheme.colorScheme.surface
                    }

                    val colorTexto = when {
                        opcionSeleccionada == -1 -> MaterialTheme.colorScheme.onSurface
                        esSeleccionada && esCorrecta -> MaterialTheme.colorScheme.primary
                        esSeleccionada && !esCorrecta -> MaterialTheme.colorScheme.error
                        esCorrecta && opcionSeleccionada != -1 -> MaterialTheme.colorScheme.primary
                        else -> MaterialTheme.colorScheme.onSurfaceVariant
                    }

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 5.dp)
                            .border(width = 1.dp, color = colorBorde, shape = RoundedCornerShape(12.dp))
                            .clickable {
                                if (opcionSeleccionada == -1) {
                                    opcionSeleccionada = index
                                    mensajeValidacion = ""
                                    if (index == preguntaActual.respuestaCorrecta) {
                                        respuestasCorrectas++
                                    }
                                }
                            },
                        colors = CardDefaults.cardColors(containerColor = colorFondo),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(
                            text = opcionText,
                            color = colorTexto,
                            fontSize = 14.sp,
                            fontWeight = if (esSeleccionada || (opcionSeleccionada != -1 && esCorrecta)) FontWeight.Bold else FontWeight.Normal,
                            modifier = Modifier.padding(14.dp)
                        )
                    }
                }

                // Retroalimentación explicativa tras seleccionar opción
                if (opcionSeleccionada != -1) {
                    Spacer(modifier = Modifier.height(16.dp))
                    val fueCorrecta = opcionSeleccionada == preguntaActual.respuestaCorrecta

                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = if (fueCorrecta) MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f)
                            else MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.5f)
                        ),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Text(
                                text = if (fueCorrecta) "✓ ¡Correcto!" else "✗ Respuesta incorrecta",
                                color = if (fueCorrecta) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = preguntaActual.explicacion,
                                color = MaterialTheme.colorScheme.onSurface,
                                fontSize = 12.sp,
                                lineHeight = 17.sp
                            )
                        }
                    }
                }
            }
        }

        if (mensajeValidacion.isNotEmpty()) {
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = mensajeValidacion,
                color = MaterialTheme.colorScheme.error,
                fontSize = 13.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.fillMaxWidth()
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Botón Siguiente / Ver Resultados
        Button(
            onClick = {
                if (opcionSeleccionada == -1) {
                    mensajeValidacion = "Selecciona una opción antes de continuar."
                } else {
                    if (indicePregunta < totalPreguntas - 1) {
                        indicePregunta++
                        opcionSeleccionada = -1
                        mensajeValidacion = ""
                    } else {
                        onQuizFinalizado(respuestasCorrectas, totalPreguntas)
                    }
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
                text = if (indicePregunta < totalPreguntas - 1) "Siguiente pregunta" else "Ver resultados",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
