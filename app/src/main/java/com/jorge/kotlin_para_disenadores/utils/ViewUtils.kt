package com.jorge.kotlin_para_disenadores.utils

import android.view.View
import com.jorge.kotlin_para_disenadores.constants.UiConstantes

fun actualizarEstadoVistas(activa: View? = null, inactivas: Collection<View>) {
    activa?.alpha = UiConstantes.ALPHA_ACTIVO
    inactivas.forEach { it.alpha = UiConstantes.ALPHA_INACTIVO }
}