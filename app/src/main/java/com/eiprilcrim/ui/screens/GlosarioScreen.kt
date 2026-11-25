package com.eiprilcrim.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
fun GlosarioScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(NavySecondary)
            .statusBarsPadding()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp, vertical = 20.dp)
    ) {
        Text(
            text = stringResource(R.string.modulo_glosario),
            color = TextPrimary,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = stringResource(R.string.modulo_glosario_desc),
            color = TextSecondary,
            fontSize = 14.sp
        )

        Spacer(modifier = Modifier.height(24.dp))

        listaGlosarioDemo.forEach { item ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, PurplePrimary.copy(alpha = 0.3f), RoundedCornerShape(16.dp)),
                colors = CardDefaults.cardColors(containerColor = NavyCard),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Text(
                        text = item.termino,
                        color = PurpleAccent,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = item.definicion,
                        color = TextSecondary,
                        fontSize = 13.sp,
                        lineHeight = 18.sp
                    )
                }
            }
            Spacer(modifier = Modifier.height(14.dp))
        }
    }
}
