package com.jorge.kotlin_para_disenadores.utils

import com.jorge.kotlin_para_disenadores.types.Descripciones
import com.jorge.kotlin_para_disenadores.types.Identificable

private fun Descripciones.todosLosIdentificables(): List<Identificable> {
    return listOf(
        activitiesYCiclo,
        archivosYConfiguracion,
        botones,
        componentesUi,
        conceptosFundamentales,
        estructuraProyecto,
        interaccion,
        kotlinAvanzadoYLog,
        layouts,
        notificacionesUi,
        programacionPoo,
        tiposDeDatos,
        programacionBasica
    ).flatten()
}

// Versión más eficiente que construye el mapa directamente
fun Descripciones.construirMapaIds(): Map<String, Identificable> {
    return todosLosIdentificables().associateBy { it.id }
}