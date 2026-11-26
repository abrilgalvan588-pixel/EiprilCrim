package com.eiprilcrim.ui.screens

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.eiprilcrim.domain.repository.GlosarioRepository
import com.eiprilcrim.domain.repository.ProtocoloRepository

data class TerminoGlosario(
    val termino: String,
    val definicion: String
)

val listaGlosarioDemo = listOf(
    TerminoGlosario("Cadena de Custodia", "Procedimiento de control que se aplica al indicio o evidencia física para garantizar su autenticidad e integridad."),
    TerminoGlosario("Criminogénesis", "Estudio de las causas, factores y condiciones que dan origen a la conducta antisocial."),
    TerminoGlosario("Dactiloscopía", "Disciplina que se encarga del estudio, clasificación y comparación de las impresiones dactilares."),
    TerminoGlosario("Indicio", "Todo objeto, marca, huella o señal producido en la comisión de un hecho presuntamente delictivo."),
    TerminoGlosario("Victimología", "Estudio científico de las víctimas en sus aspectos biológicos, psicológicos y sociológicos.")
)

@Composable
fun GlosarioScreen(
    tabInicial: Int = 0,
    glosarioRepository: GlosarioRepository? = null,
    protocoloRepository: ProtocoloRepository? = null
) {
    var tabSeleccionada by rememberSaveable { mutableIntStateOf(tabInicial) }
    var busqueda by rememberSaveable { mutableStateOf("") }

    var terminosPersonalizadosPairs by rememberSaveable { mutableStateOf(listOf<Pair<String, String>>()) }
    var mostrarDialogoAgregar by remember { mutableStateOf(false) }
    var nuevoTermino by remember { mutableStateOf("") }
    var nuevaDefinicion by remember { mutableStateOf("") }
    var errorTermino by remember { mutableStateOf("") }

    val terminosPersonalizados = terminosPersonalizadosPairs.map { TerminoGlosario(it.first, it.second) }

    var listaGlosario by remember { mutableStateOf<List<TerminoGlosario>>(emptyList()) }
    var listaProtocolos by remember { mutableStateOf<List<ProtocoloItem>>(emptyList()) }

    val pestanas = listOf("Glosario", "Protocolos")

    LaunchedEffect(busqueda, tabSeleccionada, glosarioRepository, protocoloRepository, terminosPersonalizados) {
        if (tabSeleccionada == 0) {
            val listaCombinada = listaGlosarioDemo + terminosPersonalizados
            if (glosarioRepository != null) {
                val terminosDb = glosarioRepository.buscarTerminos(busqueda)
                val base = if (terminosDb.isNotEmpty()) terminosDb.map { TerminoGlosario(it.termino, it.definicion) } else listaCombinada
                listaGlosario = base.filter {
                    busqueda.isBlank() || it.termino.contains(busqueda, ignoreCase = true) || it.definicion.contains(busqueda, ignoreCase = true)
                }
            } else {
                listaGlosario = listaCombinada.filter {
                    busqueda.isBlank() || it.termino.contains(busqueda, ignoreCase = true) || it.definicion.contains(busqueda, ignoreCase = true)
                }
            }
        } else {
            if (protocoloRepository != null) {
                val protocolosDb = protocoloRepository.buscarProtocolos(busqueda)
                listaProtocolos = if (protocolosDb.isNotEmpty()) {
                    protocolosDb.map { ProtocoloItem(it.nombre, it.descripcion, it.pasos) }
                } else {
                    emptyList()
                }
            } else {
                listaProtocolos = listaProtocolosDemo.filter {
                    busqueda.isBlank() || it.nombre.contains(busqueda, ignoreCase = true) || it.descripcion.contains(busqueda, ignoreCase = true)
                }
            }
        }
    }

    if (mostrarDialogoAgregar) {
        AlertDialog(
            onDismissRequest = {
                mostrarDialogoAgregar = false
                nuevoTermino = ""
                nuevaDefinicion = ""
                errorTermino = ""
            },
            title = { Text("Agregar nuevo término") },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    OutlinedTextField(
                        value = nuevoTermino,
                        onValueChange = { nuevoTermino = it; errorTermino = "" },
                        label = { Text("Término") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = nuevaDefinicion,
                        onValueChange = { nuevaDefinicion = it; errorTermino = "" },
                        label = { Text("Definición") },
                        minLines = 3,
                        modifier = Modifier.fillMaxWidth()
                    )
                    if (errorTermino.isNotEmpty()) {
                        Text(
                            text = errorTermino,
                            color = MaterialTheme.colorScheme.error,
                            fontSize = 12.sp
                        )
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = {
                    if (nuevoTermino.isBlank() || nuevaDefinicion.isBlank()) {
                        errorTermino = "Completa ambos campos."
                    } else {
                        terminosPersonalizadosPairs = terminosPersonalizadosPairs + (nuevoTermino.trim() to nuevaDefinicion.trim())
                        mostrarDialogoAgregar = false
                        nuevoTermino = ""
                        nuevaDefinicion = ""
                        errorTermino = ""
                    }
                }) {
                    Text("Guardar", color = MaterialTheme.colorScheme.primary)
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    mostrarDialogoAgregar = false
                    nuevoTermino = ""
                    nuevaDefinicion = ""
                    errorTermino = ""
                }) {
                    Text("Cancelar")
                }
            },
            containerColor = MaterialTheme.colorScheme.surface,
            titleContentColor = MaterialTheme.colorScheme.onSurface,
            textContentColor = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background,
        floatingActionButton = {
            if (tabSeleccionada == 0) {
                FloatingActionButton(
                    onClick = { mostrarDialogoAgregar = true },
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary,
                    shape = CircleShape
                ) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = "Agregar término")
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(MaterialTheme.colorScheme.background)
        ) {
            // TabRow para cambiar entre Glosario y Protocolos
            TabRow(
                selectedTabIndex = tabSeleccionada,
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.primary,
                indicator = { tabPositions ->
                    TabRowDefaults.SecondaryIndicator(
                        modifier = Modifier.tabIndicatorOffset(tabPositions[tabSeleccionada]),
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            ) {
                pestanas.forEachIndexed { index, titulo ->
                    Tab(
                        selected = tabSeleccionada == index,
                        onClick = { tabSeleccionada = index },
                        text = {
                            Text(
                                text = titulo,
                                fontWeight = if (tabSeleccionada == index) FontWeight.Bold else FontWeight.Normal,
                                fontSize = 15.sp,
                                color = if (tabSeleccionada == index) MaterialTheme.colorScheme.primary
                                else MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    )
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 24.dp, vertical = 20.dp)
            ) {
                // SearchBar con búsqueda en tiempo real
                OutlinedTextField(
                    value = busqueda,
                    onValueChange = { busqueda = it },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = {
                        Text(
                            text = "Buscar en " + if (tabSeleccionada == 0) "glosario..." else "protocolos...",
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            fontSize = 14.sp
                        )
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Buscar",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    },
                    singleLine = true,
                    shape = CircleShape,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = MaterialTheme.colorScheme.surface,
                        unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                        focusedTextColor = MaterialTheme.colorScheme.onSurface,
                        unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f),
                        cursorColor = MaterialTheme.colorScheme.primary
                    )
                )

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = "Conceptos esenciales",
                    color = MaterialTheme.colorScheme.onBackground,
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "Tu referencia durante cada investigación.",
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontSize = 14.sp
                )

                Spacer(modifier = Modifier.height(24.dp))

                if (tabSeleccionada == 0) {
                    if (listaGlosario.isEmpty()) {
                        Text(
                            text = "No se encontraron términos",
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            fontSize = 14.sp,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                    } else {
                        listaGlosario.forEach { item ->
                            TarjetaExpandibleGlosario(item = item)
                            Spacer(modifier = Modifier.height(12.dp))
                        }
                    }
                } else {
                    if (listaProtocolos.isEmpty()) {
                        Text(
                            text = "No se encontraron protocolos",
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            fontSize = 14.sp,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                    } else {
                        listaProtocolos.forEach { protocolo ->
                            TarjetaProtocoloPasos(protocolo = protocolo)
                            Spacer(modifier = Modifier.height(14.dp))
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun TarjetaExpandibleGlosario(item: TerminoGlosario) {
    var expandido by rememberSaveable { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.outline.copy(alpha = 0.4f),
                shape = RoundedCornerShape(16.dp)
            )
            .clickable { expandido = !expandido },
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(modifier = Modifier.padding(18.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = item.termino,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f)
                )

                Icon(
                    imageVector = if (expandido) Icons.Default.Remove else Icons.Default.Add,
                    contentDescription = if (expandido) "Colapsar" else "Expandir",
                    tint = MaterialTheme.colorScheme.primary
                )
            }

            AnimatedVisibility(visible = expandido) {
                Column {
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = item.definicion,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontSize = 13.sp,
                        lineHeight = 19.sp
                    )
                }
            }
        }
    }
}

@Composable
private fun TarjetaProtocoloPasos(protocolo: ProtocoloItem) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.outline.copy(alpha = 0.4f),
                shape = RoundedCornerShape(16.dp)
            ),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(modifier = Modifier.padding(18.dp)) {
            Text(
                text = protocolo.nombre,
                color = MaterialTheme.colorScheme.primary,
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = protocolo.descripcion,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontSize = 13.sp,
                lineHeight = 18.sp
            )

            Spacer(modifier = Modifier.height(14.dp))

            Text(
                text = "Pasos de actuación:",
                color = MaterialTheme.colorScheme.onSurface,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(6.dp))

            protocolo.pasos.forEach { paso ->
                Text(
                    text = paso,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontSize = 12.sp,
                    lineHeight = 18.sp,
                    modifier = Modifier.padding(vertical = 3.dp)
                )
            }
        }
    }
}
