package com.eiprilcrim.ui.screens

import androidx.compose.runtime.Composable

data class ProtocoloItem(
    val nombre: String,
    val descripcion: String,
    val pasos: List<String> = emptyList()
)

val listaProtocolosDemo = listOf(
    ProtocoloItem(
        nombre = "Preservación del Lugar del Hecho",
        descripcion = "Acordonamiento de la zona, acristalamiento y registro de accesos autorizados.",
        pasos = listOf(
            "1. Acordonar el área primaria y secundaria.",
            "2. Establecer una ruta segura de acceso para peritos.",
            "3. Registrar a toda persona autorizada en la escena.",
            "4. Evitar cualquier manipulación o contaminación de indicios."
        )
    ),
    ProtocoloItem(
        nombre = "Fijación Fotográfica Forense",
        descripcion = "Toma de fotografías panorámicas, plano medio y gran acercamiento con escala métrica.",
        pasos = listOf(
            "1. Fotografías panorámicas generales de la escena.",
            "2. Tomas en plano medio asociando los indicios entre sí.",
            "3. Acercamientos con testigo métrico a cada indicio.",
            "4. Registro minucioso en la ficha pericial."
        )
    ),
    ProtocoloItem(
        nombre = "Embalaje de Evidencia Biológica",
        descripcion = "Secado previo al aire, uso de bolsas de papel estériles y etiquetado con sello de inviolabilidad.",
        pasos = listOf(
            "1. Secado al aire en ambiente controlado sin calor directo.",
            "2. Colocación en bolsa o sobre de papel sin plastificar.",
            "3. Sellado hermético con cinta de inviolabilidad.",
            "4. Rotulado y firma de cadena de custodia."
        )
    )
)

@Composable
fun ProtocolosScreen() {
    GlosarioScreen(tabInicial = 1)
}
