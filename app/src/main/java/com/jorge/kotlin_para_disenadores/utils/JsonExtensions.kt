package com.jorge.kotlin_para_disenadores.utils

import com.jorge.kotlin_para_disenadores.types.Descripciones
import com.jorge.kotlin_para_disenadores.types.Identificable

fun Descripciones.todosLosIdentificables(): List<Identificable> {
    val resultado = mutableListOf<Identificable>()

    if (layouts.isNotEmpty()) resultado.addAll(layouts)
    if (componentesUI.isNotEmpty()) resultado.addAll(componentesUI)
    if (archivosYCarpetas.isNotEmpty()) resultado.addAll(archivosYCarpetas)
    if (conceptosClave.isNotEmpty()) resultado.addAll(conceptosClave)

    return resultado
}
