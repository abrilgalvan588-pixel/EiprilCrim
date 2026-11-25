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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
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

data class CasoItem(
    val id: Int,
    val titulo: String,
    val descripcion: String,
    val categoria: String
)

val listaCasosDemo = listOf(
    CasoItem(1, "Análisis de Escena en Incendio", "Investigación de patrones de quemaduras e indicios de acelerantes.", "Incendios Forenses"),
    CasoItem(2, "Cadena de Custodia Digital", "Preservación, duplicación previa y análisis forense de dispositivos informáticos.", "Informática Forense"),
    CasoItem(3, "Huellas Dactilares Latentes", "Levantamiento, revelado e identificación de dactilogramas en superficies no porosas.", "Dactiloscopía"),
    CasoItem(4, "Balística e Indicios Hemáticos", "Reconstrucción de trayectorias balísticas y patrones de manchas de sangre.", "Criminalística de Campo")
)

@Composable
fun CasosScreen(
    onCasoClick: (Int) -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(NavySecondary)
            .statusBarsPadding()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp, vertical = 20.dp)
    ) {
        Text(
            text = stringResource(R.string.modulo_casos),
            color = TextPrimary,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = stringResource(R.string.modulo_casos_desc),
            color = TextSecondary,
            fontSize = 14.sp
        )

        Spacer(modifier = Modifier.height(24.dp))

        listaCasosDemo.forEach { caso ->
            TarjetaCaso(
                caso = caso,
                onClick = { onCasoClick(caso.id) }
            )
            Spacer(modifier = Modifier.height(14.dp))
        }
    }
}

@Composable
private fun TarjetaCaso(
    caso: CasoItem,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = PurplePrimary.copy(alpha = 0.3f),
                shape = RoundedCornerShape(16.dp)
            )
            .clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = NavyCard),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(modifier = Modifier.padding(18.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = caso.titulo,
                    color = TextPrimary,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = caso.categoria,
                    color = PurpleAccent,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = caso.descripcion,
                color = TextSecondary,
                fontSize = 13.sp,
                lineHeight = 18.sp
            )
        }
    }
}
