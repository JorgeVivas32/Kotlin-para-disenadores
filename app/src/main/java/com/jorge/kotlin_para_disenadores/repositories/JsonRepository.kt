package com.jorge.kotlin_para_disenadores.repositories

import android.content.Context
import com.jorge.kotlin_para_disenadores.types.Identificable
import com.jorge.kotlin_para_disenadores.utils.cargarJsonDesdeAssets
import com.jorge.kotlin_para_disenadores.utils.construirMapaIds

object JsonRepository {
    private lateinit var lookupMap: Map<String, Identificable>
    private lateinit var appContext: Context

    fun init(context: Context) {
        appContext = context.applicationContext
        val data = appContext.cargarJsonDesdeAssets("descripciones.json")
        lookupMap = data.construirMapaIds()
    }

    fun getItemPorId(id: String) = lookupMap[id]
}