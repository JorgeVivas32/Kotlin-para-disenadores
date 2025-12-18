package com.jorge.kotlin_para_disenadores.utils

import android.content.Context
import com.google.gson.Gson
import com.jorge.kotlin_para_disenadores.types.Descripciones

private val gson = Gson()

fun Context.cargarJsonDesdeAssets(nombreArchivo: String): Descripciones {
    val jsonString = assets.open(nombreArchivo).bufferedReader().use { it.readText() }

    return gson.fromJson(jsonString, Descripciones::class.java)
}