package com.jorge.kotlin_para_disenadores.utils

import com.jorge.kotlin_para_disenadores.types.Descripciones
import com.jorge.kotlin_para_disenadores.types.Identificable

private fun Descripciones.todosLosIdentificables(): List<Identificable> {
    return listOf(
        layouts,
        botones,
        componentesUi,
        notificacionesUi,
        activitiesYCiclo,
        archivosYConfiguracion,
        programacionPoo,
        tiposDeDatos,
        kotlinAvanzadoYLog
    ).flatten()
}

// Versión más eficiente que construye el mapa directamente
fun Descripciones.construirMapaIds(): Map<String, Identificable> {
    return todosLosIdentificables().associateBy { it.id }
}