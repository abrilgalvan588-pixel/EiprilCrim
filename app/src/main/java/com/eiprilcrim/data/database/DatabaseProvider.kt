package com.eiprilcrim.data.database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.eiprilcrim.data.entity.CasoEntity
import com.eiprilcrim.data.entity.GlosarioEntity
import com.eiprilcrim.data.entity.ProtocoloEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

object DatabaseProvider {

    @Volatile
    private var INSTANCE: AppDatabase? = null

    fun getDatabase(context: Context): AppDatabase {
        return INSTANCE ?: synchronized(this) {
            val instance = Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "eiprilcrim_database"
            )
            .fallbackToDestructiveMigration()
            .addCallback(object : RoomDatabase.Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    CoroutineScope(Dispatchers.IO).launch {
                        seedDatabase(INSTANCE ?: return@launch)
                    }
                }
            })
            .build()

            INSTANCE = instance
            instance
        }
    }

    private suspend fun seedDatabase(db: AppDatabase) {
        val casosIniciales = listOf(
            CasoEntity(
                id = 1,
                titulo = "Observación y registro de indicios",
                descripcion = "Análisis inicial y preservación fotográfica de la evidencia en el lugar de los hechos.",
                categoria = "Criminalística de Campo",
                nivel = "Fácil"
            ),
            CasoEntity(
                id = 2,
                titulo = "Reconstrucción de una secuencia",
                descripcion = "Estudio de la dinámica de los hechos y cronología de indicios.",
                categoria = "Análisis Secuencial",
                nivel = "Medio"
            ),
            CasoEntity(
                id = 3,
                titulo = "Contraste de hipótesis",
                descripcion = "Evaluación de hipótesis criminológicas enfrentadas y validez de pruebas.",
                categoria = "Metodología Forense",
                nivel = "Difícil"
            )
        )
        db.casoDao().insertarTodos(casosIniciales)

        val glosarioInicial = listOf(
            GlosarioEntity(id = 1, termino = "Cadena de Custodia", definicion = "Procedimiento de control que se aplica al indicio o evidencia física para garantizar su autenticidad e integridad."),
            GlosarioEntity(id = 2, termino = "Criminogénesis", definicion = "Estudio de las causas, factores y condiciones que dan origen a la conducta antisocial."),
            GlosarioEntity(id = 3, termino = "Dactiloscopía", definicion = "Disciplina que se encarga del estudio, clasificación y comparación de las impresiones dactilares."),
            GlosarioEntity(id = 4, termino = "Indicio", definicion = "Todo objeto, marca, huella o señal producido en la comisión de un hecho presuntamente delictivo."),
            GlosarioEntity(id = 5, termino = "Victimología", definicion = "Estudio científico de las víctimas en sus aspectos biológicos, psicológicos y sociológicos.")
        )
        db.glosarioDao().insertarTodos(glosarioInicial)

        val protocolosIniciales = listOf(
            ProtocoloEntity(
                id = 1,
                nombre = "Preservación del Lugar del Hecho",
                descripcion = "Acordonamiento de la zona, acristalamiento y registro de accesos autorizados.",
                pasosDelimitados = "1. Acordonar el área primaria y secundaria.||2. Establecer una ruta segura de acceso para peritos.||3. Registrar a toda persona autorizada en la escena.||4. Evitar cualquier manipulación o contaminación de indicios."
            ),
            ProtocoloEntity(
                id = 2,
                nombre = "Fijación Fotográfica Forense",
                descripcion = "Toma de fotografías panorámicas, plano medio y gran acercamiento con escala métrica.",
                pasosDelimitados = "1. Fotografías panorámicas generales de la escena.||2. Tomas en plano medio asociando los indicios entre sí.||3. Acercamientos con testigo métrico a cada indicio.||4. Registro minucioso en la ficha pericial."
            ),
            ProtocoloEntity(
                id = 3,
                nombre = "Embalaje de Evidencia Biológica",
                descripcion = "Secado previo al aire, uso de bolsas de papel estériles y etiquetado con sello de inviolabilidad.",
                pasosDelimitados = "1. Secado al aire en ambiente controlado sin calor directo.||2. Colocación en bolsa o sobre de papel sin plastificar.||3. Sellado hermético con cinta de inviolabilidad.||4. Rotulado y firma de cadena de custodia."
            )
        )
        db.protocoloDao().insertarTodos(protocolosIniciales)
    }
}
